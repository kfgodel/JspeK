package info.kfgodel.jspek.impl.context;

import info.kfgodel.jspek.api.contexts.ClassBasedTestContext;
import info.kfgodel.jspek.api.exceptions.SpecException;
import info.kfgodel.jspek.impl.model.TestContextDefinition;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * This type implements a test context that has a parent context
 * Created by kfgodel on 20/07/14.
 */
public class MappedContext implements TestContextDefinition, ClassBasedTestContext<Object> {

  public static final String DESCRIBED_CLASS_VARIABLE_NAME = "describedClass";
  public static final String SUBJECT_VARIABLE_NAME = "subject";
  public static final String SETUP_CODE_VARIABLE_NAME = "setupCode";
  public static final String EXERCISE_CODE_VARIABLE_NAME = "exerciseCode";
  public static final String ASSERTION_CODE_VARIABLE_NAME = "assertionCode";

  private TestContextDefinition parentDefinition;
  private Map<String, Supplier<Object>> variableDefinitions;
  private Map<String, Object> variableValues;

  @Override
  public void let(String variableName, Supplier<?> valueDefinition) throws SpecException {
    if (this.containsValueFor(variableName)) {
      throw new SpecException("Variable [" + variableName + "] cannot be re-defined once assigned. Current value: [" + get(variableName) + "]");
    }
    this.storeDefinitionFor(variableName, valueDefinition);
  }

  @Override
  public <T> T get(String variableName) {
    if (this.containsValueFor(variableName)) {
      // Use cached value
      return getValueFor(variableName);
    }

    Optional<Supplier<T>> variableDefinition = (Optional) getDefinitionFor(variableName);
    if (!variableDefinition.isPresent()) {
      throw new SpecException("Variable [" + variableName + "] must be defined before accessing it in current context[" + this.getVariableDefinitions() + "]");
    }
    return this.createNewValueFrom(variableDefinition.get(), variableName);
  }

  @Override
  public boolean hasDefinitionFor(String variableName) {
    return getDefinitionFor(variableName).isPresent();
  }

  @Override
  public Runnable setupCode() {
    return get(SETUP_CODE_VARIABLE_NAME);
  }

  @Override
  public void setupCode(Supplier<Runnable> definition) {
    let(SETUP_CODE_VARIABLE_NAME, definition);
  }

  @Override
  public Runnable exerciseCode() {
    return get(EXERCISE_CODE_VARIABLE_NAME);
  }

  @Override
  public void exerciseCode(Supplier<Runnable> definition) {
    let(EXERCISE_CODE_VARIABLE_NAME, definition);
  }

  @Override
  public Runnable assertionCode() {
    return get(ASSERTION_CODE_VARIABLE_NAME);
  }

  @Override
  public void assertionCode(Supplier<Runnable> definition) {
    let(ASSERTION_CODE_VARIABLE_NAME, definition);
  }

  private <T> T createNewValueFrom(Supplier<T> variableDefinition, String variableName) {
    try {
      T variableValue = variableDefinition.get();
      storeValueFor(variableName, variableValue);
      return variableValue;
    } catch (StackOverflowError e) {
      throw new SpecException("Got a Stackoverflow when evaluating variable [" + variableName + "]. Do we have a cyclic dependency on its definition?", e);
    }
  }

  /**
   * Stores the definition for a variable in this context
   *
   * @param variableName    The name of the variable
   * @param valueDefinition The variable definition
   */
  private void storeDefinitionFor(String variableName, Supplier<?> valueDefinition) {
    getVariableDefinitions().put(variableName, (Supplier<Object>) valueDefinition);
  }

  private <T> T getValueFor(String variableName) {
    if (variableValues == null) {
      return null;
    }
    return (T) getVariableValues().get(variableName);
  }

  private void storeValueFor(String variableName, Object variableValue) {
    getVariableValues().put(variableName, variableValue);
  }

  @Override
  public Optional<Supplier<Object>> getDefinitionFor(String variableName) {
    boolean weDontHaveADefinition = variableDefinitions == null || !variableDefinitions.containsKey(variableName);
    if (weDontHaveADefinition) {
      return getParentDefinition().getDefinitionFor(variableName);
    }

    return Optional.ofNullable(getVariableDefinitions().get(variableName));
  }

  /**
   * Indicates if this context has a value defined for a named variable
   *
   * @param variableName The name of the variable
   * @return true if the variable was already resolved
   */
  private boolean containsValueFor(String variableName) {
    if (variableValues == null) {
      return false;
    }
    return getVariableValues().containsKey(variableName);
  }

  public static MappedContext create() {
    MappedContext context = new MappedContext();
    context.parentDefinition = NullContextDefinition.create();
    return context;
  }

  @Override
  public void setParentDefinition(TestContextDefinition parentDefinition) {
    this.parentDefinition = parentDefinition;
  }

  @Override
  public TestContextDefinition getParentDefinition() {
    return parentDefinition;
  }

  private Map<String, Supplier<Object>> getVariableDefinitions() {
    if (variableDefinitions == null) {
      variableDefinitions = new HashMap<>();
    }
    return variableDefinitions;
  }

  private Map<String, Object> getVariableValues() {
    if (variableValues == null) {
      variableValues = new HashMap<>();
    }
    return variableValues;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + ": " + variableDefinitions;
  }

  @Override
  public void describedClass(Supplier<Class<Object>> definition) {
    let(DESCRIBED_CLASS_VARIABLE_NAME, definition);
  }

  @Override
  public void subject(Supplier<Object> definition) {
    let(SUBJECT_VARIABLE_NAME, definition);
  }

  /**
   * Overridden to improve the error message when not defined
   */
  @Override
  public Class<Object> describedClass() throws SpecException {
    if (lacksVariableDefinitionFor(DESCRIBED_CLASS_VARIABLE_NAME)) {
      throw new SpecException("Described class is not defined in this context[" + this.getVariableDefinitions() + "].\nUse describe(class,lambda) to define it before accessing it");
    }
    return get(DESCRIBED_CLASS_VARIABLE_NAME);
  }

  /**
   * Indicates if this context has a definition for the given variable
   */
  private boolean lacksVariableDefinitionFor(String variableName) {
    return !this.getDefinitionFor(variableName).isPresent();
  }

  /**
   * Overridden to improve the error message when not defined and
   * lazily define a default subject
   */
  @Override
  public Object subject() {
    if (lacksVariableDefinitionFor(SUBJECT_VARIABLE_NAME)) {
      tryToDefineADefaultSubject();
    }
    return get(SUBJECT_VARIABLE_NAME);
  }

  private void tryToDefineADefaultSubject() {
    if(lacksVariableDefinitionFor(DESCRIBED_CLASS_VARIABLE_NAME)){
      throw new SpecException("Subject is not defined in this context[" + this.getVariableDefinitions() + "].\nUse describe(class,lambda) to define a class whose subject is going to be tested");
    }
    Class<Object> testedClass = describedClass();
    subject(()-> this.instantiateSubjectFrom(testedClass));
  }

  private Object instantiateSubjectFrom(Class<Object> testedClass) {
    try {
      return testedClass.newInstance();
    } catch (Exception e) {
      throw new SpecException("Unknown error trying to instantiate subject", e);
    }
  }


}

package ar.com.dgarcia.javaspec.impl.context;

import ar.com.dgarcia.javaspec.api.exceptions.SpecException;
import ar.com.dgarcia.javaspec.impl.model.TestContextDefinition;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * This type implements a test context that has a parent context
 * Created by kfgodel on 20/07/14.
 */
public class MappedContext implements TestContextDefinition {

  public static final String DESCRIBED_CLASS_VARIABLE_NAME = "describedClass";

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

    Supplier<Object> variableDefinition = getDefinitionFor(variableName);
    if (variableDefinition == null) {
      throw new SpecException("Variable [" + variableName + "] cannot be accessed because it lacks a definition in this context[" + this.getVariableDefinitions() + "]");
    }
    Object variableValue = null;
    try {
      variableValue = variableDefinition.get();
    } catch (SpecException e) {
      throw e;
    } catch (StackOverflowError e) {
      throw new SpecException("Got a Stackoverflow when evaluating variable [" + variableName + "]. Do we have a cyclic dependency on its definition?", e);
    } catch (Exception e) {
      throw new SpecException("Definition for variable [" + variableName + "] failed to execute: " + e.getMessage(), e);
    }
    storeValueFor(variableName, variableValue);
    return (T) variableValue;
  }

  @Override
  public void describedClass(Supplier<Class> definition) {
    let(DESCRIBED_CLASS_VARIABLE_NAME, definition);
  }

  @Override
  public <T> Class<T> describedClass() throws SpecException {
    return get(DESCRIBED_CLASS_VARIABLE_NAME);
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
  public Supplier<Object> getDefinitionFor(String variableName) {
    boolean weDontHaveADefinition = variableDefinitions == null || !variableDefinitions.containsKey(variableName);
    if (weDontHaveADefinition) {
      return getParentDefinition().getDefinitionFor(variableName);
    }

    return getVariableDefinitions().get(variableName);
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
}

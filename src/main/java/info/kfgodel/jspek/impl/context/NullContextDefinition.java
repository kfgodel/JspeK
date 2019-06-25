package info.kfgodel.jspek.impl.context;

import info.kfgodel.jspek.api.exceptions.SpecException;
import info.kfgodel.jspek.impl.model.TestContextDefinition;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * This type is a null implementation of context definition for root contexts
 * Created by kfgodel on 20/07/14.
 */
public class NullContextDefinition implements TestContextDefinition {

  public static NullContextDefinition create() {
    NullContextDefinition definition = new NullContextDefinition();
    return definition;
  }

  @Override
  public void let(String variableName, Supplier<?> valueDefinition) throws SpecException {
    throw new UnsupportedOperationException("Null context cannot define variables: " + variableName);
  }

  @Override
  public <T> T get(String variableName) {
    throw new SpecException("Variable [" + variableName + "] cannot be accessed because lacks definition");
  }

  @Override
  public boolean hasDefinitionFor(String variableName) {
    return false;
  }

  @Override
  public Runnable setupCode() {
    throw new SpecException("Setup code is not defined");
  }

  @Override
  public void setupCode(Supplier<Runnable> definition) {
    throw new UnsupportedOperationException("Null context cannot define setup code");
  }

  @Override
  public Runnable exerciseCode() {
    throw new SpecException("Exercise code is not defined");
  }

  @Override
  public void exerciseCode(Supplier<Runnable> definition) {
    throw new UnsupportedOperationException("Null context cannot define exercise code");
  }

  @Override
  public Runnable assertionCode() {
    throw new SpecException("Assertion code is not defined");
  }

  @Override
  public void assertionCode(Supplier<Runnable> definition) {
    throw new UnsupportedOperationException("Null context cannot define assertion code");
  }

  @Override
  public void setParentDefinition(TestContextDefinition parentDefinition) {
    throw new UnsupportedOperationException("Null context cannot have parent context");
  }

  @Override
  public TestContextDefinition getParentDefinition() {
    return this;
  }

  @Override
  public Optional<Supplier<Object>> getDefinitionFor(String variableName) {
    return Optional.empty();
  }
}

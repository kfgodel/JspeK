package info.kfgodel.jspek.impl.modes;

import info.kfgodel.jspek.api.contexts.TestContext;
import info.kfgodel.jspek.api.exceptions.FailingRunnable;
import info.kfgodel.jspek.api.exceptions.SpecException;
import info.kfgodel.jspek.api.variable.Let;

import java.util.function.Consumer;

/**
 * Date: 30/03/19 - 16:36
 */
public class InitialMode implements ExecutionMode<TestContext> {

  @SuppressWarnings("unchecked")
  // We cast to type argument T to conform with expected signature, but initial mode should not be used with other test contexts
  // this casting decision may be an error but initial mode is created prior to having a context
  public static <T extends TestContext> ExecutionMode<T> create() {
    return (ExecutionMode<T>) new InitialMode();
  }

  @Override
  public void beforeEach(Runnable aCodeBlock) {
    throw new SpecException("A before each block can't be defined outside the method define");
  }

  @Override
  public void afterEach(Runnable aCodeBlock) {
    throw new SpecException("A after each block can't be defined outside the method define");
  }

  @Override
  public void it(String testName, Runnable aTestCode) {
    throw new SpecException("A test can't be defined outside the method define");
  }

  @Override
  public void xit(String testName) {
    throw new SpecException("A test can't be defined outside the method define");
  }

  @Override
  public void xit(String testName, Runnable aTestCode) {
    throw new SpecException("A test can't be ignored outside the method define");
  }

  @Override
  public <X extends Throwable> void itThrows(Class<X> expectedExceptionType, String testNameSuffix, FailingRunnable<X> aTestCode, Consumer<X> exceptionAssertions) throws SpecException {
    throw new SpecException("A test can't be defined outside the method define");
  }

  @Override
  public void describe(String aGroupName, Runnable aGroupDefinition) {
    throw new SpecException("A group can't be defined outside the method define");
  }

  @Override
  public void describe(Class<?> aClass, Runnable aGroupDefinition) {
    throw new SpecException("A group can't be defined outside the method define");
  }

  @Override
  public void xdescribe(String aGroupName, Runnable aGroupDefinition) {
    throw new SpecException("A group can't be ignored outside the method define");
  }

  @Override
  public void xdescribe(Class<?> aGroupName, Runnable aGroupDefinition) {
    throw new SpecException("A group can't be ignored outside the method define");
  }

  @Override
  public TestContext context() {
    throw new SpecException("The context is not available outside the method define");
  }

  @Override
  public <X> Let<X> localLet(String variableName) {
    throw new SpecException("A local let can't be defined outside the method define");
  }

}

package ar.com.dgarcia.javaspec.impl.modes;

import ar.com.dgarcia.javaspec.api.JavaSpecApi;
import ar.com.dgarcia.javaspec.api.contexts.TestContext;
import ar.com.dgarcia.javaspec.api.exceptions.FailingRunnable;
import ar.com.dgarcia.javaspec.api.exceptions.SpecException;
import ar.com.dgarcia.javaspec.api.variable.Let;

import java.util.function.Consumer;

/**
 * Date: 30/03/19 - 16:36
 */
public class InitialMode implements JavaSpecApi<TestContext> {

  public static <T extends TestContext> JavaSpecApi<T> create() {
    return (JavaSpecApi<T>) new InitialMode();
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
  public void it(String testName) {
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

  @Override
  public void given(Runnable setupCode) {
    throw new SpecException("A setup block can't be defined outside the method define");
  }

  @Override
  public void when(Runnable exerciseCode) {
    throw new SpecException("An exercise block can't be defined outside the method define");
  }

  @Override
  public void then(Runnable assertionCode) {
    throw new SpecException("An assertion block can't be defined outside the method define");
  }

  @Override
  public void itThen(String testName, Runnable assertionCode) {
    throw new SpecException("A test can't be defined outside the method define");
  }

  @Override
  public void executeAsGivenWhenThenTest() {
    throw new SpecException("A test can't be run outside the method define");
  }
}

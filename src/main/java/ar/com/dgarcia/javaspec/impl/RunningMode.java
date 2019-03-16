package ar.com.dgarcia.javaspec.impl;

import ar.com.dgarcia.javaspec.api.JavaSpecApi;
import ar.com.dgarcia.javaspec.api.contexts.TestContext;
import ar.com.dgarcia.javaspec.api.exceptions.FailingRunnable;
import ar.com.dgarcia.javaspec.api.exceptions.SpecException;
import ar.com.dgarcia.javaspec.api.variable.Let;

import java.util.function.Consumer;

/**
 * This type represents the available api when the tests are being run.<br>
 *   Through an instance of this class a javaspec enforces the correct usage
 *   or allows behavior not available when the tests are being defined
 *
 * Created by kfgodel on 09/03/16.
 */
public class RunningMode<T extends TestContext> implements JavaSpecApi<T> {

  private T currentContext;

  /**
   * Creates a new running mode that will delegate safe calls to the previous mode
   * @param executionContext
   */
  public static<T extends TestContext> RunningMode<T> create(T executionContext) {
    RunningMode<T> api = new RunningMode<>();
    api.currentContext = executionContext;
    return api;
  }

  @Override
  public T context() {
    return currentContext;
  }

  @Override
  public void given(Runnable setupCode) {
    context().setupCode(()-> setupCode);
  }

  @Override
  public void when(Runnable exerciseCode) {
    context().exerciseCode(()-> exerciseCode);
  }

  @Override
  public void then(Runnable assertionCode) {
    context().assertionCode(()-> assertionCode);
    executeAsGivenWhenThenTest();
  }

  @Override
  public void itThen(String testName, Runnable assertionCode) {
    throw new SpecException("A running test cannot declare a nested itThen() sub-test");
  }

  @Override
  public void executeAsGivenWhenThenTest() {
    context().setupCode().run();
    context().exerciseCode().run();
    context().assertionCode().run();
  }

  @Override
  public void xdescribe(String aGroupName, Runnable aGroupDefinition) {
    throw new SpecException("A running test cannot declare an ignored group spec calling xdescribe()");
  }

  @Override
  public void xdescribe(Class<?> aGroupName, Runnable aGroupDefinition) {
    throw new SpecException("A running test cannot declare an ignored class spec calling xdescribe()");
  }

  @Override
  public void describe(Class<?> aClass, Runnable aGroupDefinition) {
    throw new SpecException("A running test cannot declare a class spec calling describe()");
  }

  @Override
  public void describe(String aGroupName, Runnable aGroupDefinition) {
    throw new SpecException("A running test cannot declare a group spec calling describe()");
  }

  @Override
  public void xit(String testName, Runnable aTestCode) {
    throw new SpecException("A running test cannot declare a nested ignored spec calling xit()");
  }

  @Override
  public void it(String testName) {
    throw new SpecException("A running test cannot declare a nested ignored spec calling it()");
  }

  @Override
  public void it(String testName, Runnable aTestCode) {
    throw new SpecException("A running test cannot declare a nested spec calling it()");
  }

  @Override
  public void afterEach(Runnable aCodeBlock) {
    throw new SpecException("A running test cannot declare an after block calling afterEach()");
  }

  @Override
  public void beforeEach(Runnable aCodeBlock) {
    throw new SpecException("A running test cannot declare a before block calling beforeEach()");
  }

  @Override
  public <X extends Throwable> void itThrows(Class<X> expectedExceptionType, String testNameSuffix, FailingRunnable<X> aTestCode, Consumer<X> exceptionAssertions) throws SpecException {
    throw new SpecException("A running test cannot declare a nested failure test calling itThrows()");
  }

  @Override
  public <X> Let<X> localLet(String variableName) {
    throw new SpecException("A running test cannot declare a let object");
  }
}

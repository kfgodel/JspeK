package info.kfgodel.jspek.impl.modes;

import info.kfgodel.jspek.api.contexts.TestContext;
import info.kfgodel.jspek.api.exceptions.FailingRunnable;
import info.kfgodel.jspek.api.exceptions.SpecException;
import info.kfgodel.jspek.api.variable.Let;

import java.util.function.Consumer;

/**
 * This type represents the available api when the tests are being run.<br>
 * Through an instance of this class a javaspec enforces the correct usage
 * or allows behavior not available when the tests are being defined
 * <p>
 * Created by kfgodel on 09/03/16.
 */
public class RunningMode<T extends TestContext> implements ExecutionMode<T> {

  private T currentContext;

  /**
   * Creates a new running mode that will delegate safe calls to the previous mode
   *
   * @param executionContext
   */
  public static <T extends TestContext> RunningMode<T> create(T executionContext) {
    RunningMode<T> api = new RunningMode<>();
    api.currentContext = executionContext;
    return api;
  }

  @Override
  public T context() {
    return currentContext;
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
  public void xit(String testName) {
    throw new SpecException("A running test cannot declare a nested ignored spec calling xit()");
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
  public <X extends Throwable> void itThrows(Class<X> expectedExceptionType, String testNameSuffix, FailingRunnable<? extends X> aFailingTestCode, Consumer<? super X> exceptionAssertions) throws SpecException {
    throw new SpecException("A running test cannot declare a nested failure test calling itThrows()");
  }

  @Override
  public <X> Let<X> localLet(String variableName) {
    throw new SpecException("A running test cannot declare a let object");
  }

  @Override
  public T test()  {
    return this.context();
  }
}

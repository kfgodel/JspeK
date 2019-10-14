package info.kfgodel.jspek.api;

import info.kfgodel.jspek.api.contexts.TestContext;
import info.kfgodel.jspek.api.exceptions.FailingRunnable;
import info.kfgodel.jspek.api.exceptions.SpecException;
import info.kfgodel.jspek.api.variable.Let;

import java.util.function.Consumer;

/**
 * This type defines the contract that spec implementation can use to define their specs
 * <p>
 * @param <T> Type of text context used to access test variables
 * Created by kfgodel on 09/03/16.
 */
public interface JavaSpecApi<T extends TestContext> {
  /**
   * Code to execute before every it() test. It's scoped to the parent describe() or define(),
   * and executed before every child it() test (direct or nested).<br>
   *
   * @param aCodeBlock A code to execute before every test
   */
  void beforeEach(Runnable aCodeBlock);

  /**
   * Code to execute after every it() test. It's scoped to the parent describe() or define(),
   * and executed after every child it() test (direct or nested).<br>
   *
   * @param aCodeBlock block of code to execute
   */
  void afterEach(Runnable aCodeBlock);

  /**
   * Defines the test code to be run as a test.<br> Every parent beforeEach() is executed prior
   * to this test. Every parent afterEach() is executed after.
   *
   * @param testName  The name to identify this test
   * @param aTestCode The code that defines the test
   */
  void it(String testName, Runnable aTestCode);

  /**
   * Declares a pending test that will be listed as ignored
   *
   * @param testName The name to identify this test
   */
  void xit(String testName);

  /**
   * Declares an ignored test. It will not be run, but listed
   *
   * @param testName  The name to identify this test
   * @param aTestCode The ignored code of this tests
   */
  void xit(String testName, Runnable aTestCode);

  /**
   * Declares a test that should fail with the expected exception type
   *
   * @param expectedExceptionType The type of exception that test should generate as a valid case
   * @param testNameSuffix        The condition description to append as test name
   * @param aTestCode             The code to run that should fail with an exception
   * @param exceptionAssertions   The code that defines assertions to be tested against the expected exception
   * @param <X>                   The type of excepction
   * @throws SpecException If the code didn't fail, or failed with another exception
   */
  <X extends Throwable> void itThrows(Class<X> expectedExceptionType, String testNameSuffix,
                                      FailingRunnable<X> aTestCode,
                                      Consumer<X> exceptionAssertions) throws SpecException;

  /**
   * Describes a set of test, and allows nesting of scenarios
   *
   * @param aGroupName       The name of the test group
   * @param aGroupDefinition The code that defines sub-tests, or sub-contexts
   */
  void describe(String aGroupName, Runnable aGroupDefinition);

  /**
   * Describes a set of tests centered on the given class as test subject
   *
   * @param aClass           The class to test
   * @param aGroupDefinition The code that defines sub-tests, or sub-contexts
   */
  void describe(Class<?> aClass, Runnable aGroupDefinition);

  /**
   * Declares a disabled suite of tests. Any sub-test or sub-context will be ignored (listed but not run)
   *
   * @param aGroupName       The name identifying the group
   * @param aGroupDefinition The code that defines sub-tests, or sub-contexts
   */
  void xdescribe(String aGroupName, Runnable aGroupDefinition);

  /**
   * Declares a disabled suite of class based tests.
   * Any sub-test or sub-context will be ignored (listed but not run)
   *
   * @param aGroupName       The name identifying the group
   * @param aGroupDefinition The code that defines sub-tests, or sub-contexts
   */
  void xdescribe(Class<?> aGroupName, Runnable aGroupDefinition);

  /**
   * Allows access to test context, to define variables or to access them.<br>
   * Usually you define variables in suites and access them in tests
   *
   * @return The current test context
   */
  T context();

  /**
   * Declares a reified local let, a named variable which may be defined in the current
   * context or be redefined by a subcontext
   *
   * @param variableName The name to identify the variable
   * @param <X>          The type of that variable
   * @return A let object that allows to define the value of the variable and obtain it
   */
  <X> Let<X> localLet(String variableName);

  /**
   * Allows access to test context. This is an alias to {@link #context()} to improve readability when needed or ambiguous
   *
   * @return The current test context
   */
  default T test() {
    return this.context();
  }

}

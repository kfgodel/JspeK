package ar.com.dgarcia.javaspec.api;

import ar.com.dgarcia.javaspec.api.contexts.TestContext;
import ar.com.dgarcia.javaspec.api.exceptions.FailingRunnable;
import ar.com.dgarcia.javaspec.api.exceptions.SpecException;

import java.util.function.Consumer;

/**
 * This type defines the contract that spec implementation can use to define their specs
 * <p>
 * Created by kfgodel on 09/03/16.
 */
public interface JavaSpecApi<T extends TestContext> {
  /**
   * Code to execute before every it() test. It's scoped to the parent describe() or define(), and executed before every child it() test (direct or nested).<br>
   *
   * @param aCodeBlock A code to execute before every test
   */
  void beforeEach(Runnable aCodeBlock);

  /**
   * Code to execute after every it() test. It's scoped to the parent describe() or define(), and executed after every child it() test (direct or nested).<br>
   *
   * @param aCodeBlock
   */
  void afterEach(Runnable aCodeBlock);

  /**
   * Defines the test code to be run as a test.<br> Every parent beforeEach() is executed prior to this test. Every parent afterEach() is executed after.
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
  void it(String testName);

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
  <X extends Throwable> void itThrows(Class<X> expectedExceptionType, String testNameSuffix, FailingRunnable<X> aTestCode, Consumer<X> exceptionAssertions) throws SpecException;

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
   * Defines the code to be executed on tests as part of the test setup.<br>
   * If this is the last part of the spec, then #executeAsGivenWhenThenSpec must be called
   * explicitly to run the 3 parts. The other two should be defined in previous context
   *
   * @param setupCode The code to execute at the beginning of the test to prepare
   *                  the context conditions for the exercise code
   */
  void given(Runnable setupCode);

  /**
   * Defines the exercise code that will execute some logic using the context
   * prepared by the setup code, and leave a side effect to be verified by the assertion code
   * If this is the last part of the spec, then #executeAsGivenWhenThenSpec must be called
   * explicitly to run the 3 parts. The other two should be defined in previous context
   *
   * @param exerciseCode The code that will affect the context to prepare it
   *                     for the assertion code
   */
  void when(Runnable exerciseCode);

  /**
   * Defines the assertion code to verify the state of the context is the expected.<br>
   * When this method is called during execution, it automatically calls #executeAsGivenWhenThenSpec
   * to run the 3 parts of the spec. So this should be the last declared part during execution
   *
   * @param assertionCode The code that checks the context against prepared expectations
   */
  void then(Runnable assertionCode);

  /**
   * Executes the three defined parts of the spec "given", "when", "then" in order,
   * taking them from the context.<br>
   * This can only be called during a test execution (inside the lambda of an it() element),
   * and it's not necessary when a "then" block is declared inside the spec
   */
  void executeAsGivenWhenThenTest();

}

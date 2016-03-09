package ar.com.dgarcia.javaspec.api;

import ar.com.dgarcia.javaspec.api.contexts.TestContext;

/**
 * This type defines the contract that spec implementation can use to define their specs
 *
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
   * Describes a set of test, and allows nesting of scenarios
   *
   * @param aGroupName       The name of the test group
   * @param aGroupDefinition The code that defines sub-tests, or sub-contexts
   */
  void describe(String aGroupName, Runnable aGroupDefinition);

  /**
   * Describes a set of tests centered on the given class as test subject
   *
   * @param aClass The class to test
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
}

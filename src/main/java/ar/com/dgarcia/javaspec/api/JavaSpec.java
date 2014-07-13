package ar.com.dgarcia.javaspec.api;

/**
 * This class is the extension point to add testing expressiveness with Java Specs.<br>
 *     The method idiom is copied from: http://jasmine.github.io/2.0/introduction.html.<br>
 * Created by kfgodel on 12/07/14.
 */
public abstract class JavaSpec {

    /**
     * Starting method to define the specs.<br>
     *     This method must be extended by subclasses and define any spec as calls to describe() and it()
     */
    public abstract void define();

    /**
     * Code to execute before every it() test. It's scoped to the parent describe() or define(), and executed before every child it() test (direct or nested).<br>
     * @param aCodeBlock A code to execute before every test
     */
    public void beforeEach(Runnable aCodeBlock) {

    }

    /**
     * Code to execute after every it() test. It's scoped to the parent describe() or define(), and executed after every child it() test (direct or nested).<br>
     * @param aCodeBlock
     */
    public void afterEach(Runnable aCodeBlock) {

    }

    /**
     * Defines the test code to be run as a test.<br> Every parent beforeEach() is executed prior to this test. Every parent afterEach() is executed after.
     * @param testName The name to identify this test
     * @param aTestCode The code that defines the test
     */
    public void it(String testName, Runnable aTestCode){

    }

    /**
     * Declares a pending test that will be listed as ignored
     * @param testName The name to identify this test
     */
    public void it(String testName){

    }

    /**
     * Declares an ignored pending test. It will not be run, but listed
     * @param testName The name to identify this test
     * @param aTestCode The ignored code of this tests
     */
    public void xit(String testName, Runnable aTestCode){

    }

    /**
     * Describes a set of test, and allows nesting of scenarios
     * @param aGroupName The name of the test group
     * @param aGroupDefinition The code that defines sub-tests, or sub-contexts
     */
    public void describe(String aGroupName, Runnable aGroupDefinition){

    }

    /**
     * Declares a disabled suite of tests. Any sub-test or sub-context will be ignored and not listed
     * @param aGroupName The name identifying the group
     * @param aGroupDefinition The code that defines sub-tests, or sub-contexts
     */
    public void xdescribe(String aGroupName, Runnable aGroupDefinition){

    }


}

package ar.com.dgarcia.javaspec.api;

import ar.com.dgarcia.javaspec.impl.context.typed.TypedContextFactory;
import ar.com.dgarcia.javaspec.impl.exceptions.SpecException;
import ar.com.dgarcia.javaspec.impl.model.SpecGroup;
import ar.com.dgarcia.javaspec.impl.model.SpecTree;
import ar.com.dgarcia.javaspec.impl.model.impl.GroupSpecDefinition;
import ar.com.dgarcia.javaspec.impl.model.impl.TestSpecDefinition;
import ar.com.dgarcia.javaspec.impl.parser.SpecStack;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

/**
 * This class is the extension point to add testing expressiveness with Java Specs.<br>
 *     The method idiom is copied from: http://jasmine.github.io/2.0/introduction.html.<br>
 * Created by kfgodel on 12/07/14.
 */
public abstract class  JavaSpec<T extends TestContext> {

    private SpecTree specTree;
    private SpecStack stack;
    private T typedContext;

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
        stack.getCurrentGroup().addBeforeBlock(aCodeBlock);
    }

    /**
     * Code to execute after every it() test. It's scoped to the parent describe() or define(), and executed after every child it() test (direct or nested).<br>
     * @param aCodeBlock
     */
    public void afterEach(Runnable aCodeBlock) {
        stack.getCurrentGroup().addAfterBlock(aCodeBlock);
    }

    /**
     * Defines the test code to be run as a test.<br> Every parent beforeEach() is executed prior to this test. Every parent afterEach() is executed after.
     * @param testName The name to identify this test
     * @param aTestCode The code that defines the test
     */
    public void it(String testName, Runnable aTestCode){
        addTestDefinition(testName, Optional.of(aTestCode));
    }

    /**
     * Declares a pending test that will be listed as ignored
     * @param testName The name to identify this test
     */
    public void it(String testName){
        addTestDefinition(testName, Optional.empty());
    }

    /**
     * Declares an ignored test. It will not be run, but listed
     * @param testName The name to identify this test
     * @param aTestCode The ignored code of this tests
     */
    public void xit(String testName, Runnable aTestCode){
        TestSpecDefinition createdTestDefinition = addTestDefinition(testName, Optional.of(aTestCode));
        createdTestDefinition.markAsPending();
    }

    private TestSpecDefinition addTestDefinition(String testName, Optional<Runnable> testBody) {
        TestSpecDefinition createdSpec = TestSpecDefinition.create(testName, testBody, specTree.getSharedContext());
        stack.getCurrentGroup().addTest(createdSpec);
        return createdSpec;
    }


    /**
     * Describes a set of test, and allows nesting of scenarios
     * @param aGroupName The name of the test group
     * @param aGroupDefinition The code that defines sub-tests, or sub-contexts
     */
    public void describe(String aGroupName, Runnable aGroupDefinition){
        nestGroupDefinition(aGroupName, aGroupDefinition);
    }

    /**
     * Declares a disabled suite of tests. Any sub-test or sub-context will be ignored (listed but not run)
     * @param aGroupName The name identifying the group
     * @param aGroupDefinition The code that defines sub-tests, or sub-contexts
     */
    public void xdescribe(String aGroupName, Runnable aGroupDefinition){
        GroupSpecDefinition createdGroup = nestGroupDefinition(aGroupName, aGroupDefinition);
        createdGroup.markAsDisabled();
    }

    private GroupSpecDefinition nestGroupDefinition(String aGroupName, Runnable definitionBlock) {
        GroupSpecDefinition createdGroup = GroupSpecDefinition.create(aGroupName);
        stack.runNesting(createdGroup, definitionBlock::run);
        return createdGroup;
    }


    /**
     * Uses the definition of this spec class to create the nodes in the tree.<br>
     *     The defined tree must be validated before using it
     * @param specTree The tree that will represent this spec
     */
    public void populate(SpecTree specTree) {
        this.specTree = specTree;
        SpecGroup rootGroup = this.specTree.getRootGroup();
        Variable<TestContext> sharedContext = this.specTree.getSharedContext();
        this.stack = SpecStack.create(rootGroup, sharedContext);
        Class<T> expectedContextType = this.getContextTypeFromSubclassDeclaration();
        this.typedContext = TypedContextFactory.createInstanceOf(expectedContextType, sharedContext);
        this.define();
    }

    /**
     * Allows access to test context, to define variables or to access them.<br>
     *  Usually you define variables in suites and access them in tests
     * @return The current test context
     */
    protected T context() {
        return typedContext;
    }

    public Class<T> getContextTypeFromSubclassDeclaration() {
        Class<? extends JavaSpec> subClass = getClass();
        if(!(subClass.getSuperclass().equals(JavaSpec.class))){
            throw new SpecException("A java spec class["+ subClass+ "] must inherit directly from " +JavaSpec.class);
        }
        Type generifiedJavaSpec = subClass.getGenericSuperclass();
        if(!(generifiedJavaSpec instanceof ParameterizedType)){
            throw new SpecException("JavaSpec superclass must be generified with a type of TestContext. For example JavaSpec<TestContext>");
        }
        Type contextType = ((ParameterizedType) generifiedJavaSpec).getActualTypeArguments()[0];
        if(!(contextType instanceof Class)){
            throw new SpecException("JavaSpec parameter is not a class?");
        }
        return (Class<T>) contextType;
    }
}

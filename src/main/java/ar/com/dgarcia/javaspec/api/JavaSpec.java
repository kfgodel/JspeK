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
public abstract class JavaSpec<T extends TestContext> implements JavaSpecApi<T> {

    private SpecTree specTree;
    private SpecStack stack;
    private T typedContext;

    /**
     * Starting method to define the specs.<br>
     *     This method must be extended by subclasses and define any spec as calls to describe() and it()
     */
    public abstract void define();

    @Override
    public void beforeEach(Runnable aCodeBlock) {
        stack.getCurrentGroup().addBeforeBlock(aCodeBlock);
    }

    @Override
    public void afterEach(Runnable aCodeBlock) {
        stack.getCurrentGroup().addAfterBlock(aCodeBlock);
    }

    @Override
    public void it(String testName, Runnable aTestCode){
        addTestDefinition(testName, Optional.of(aTestCode));
    }

    @Override
    public void it(String testName){
        addTestDefinition(testName, Optional.empty());
    }

    @Override
    public void xit(String testName, Runnable aTestCode){
        TestSpecDefinition createdTestDefinition = addTestDefinition(testName, Optional.of(aTestCode));
        createdTestDefinition.markAsPending();
    }

    private TestSpecDefinition addTestDefinition(String testName, Optional<Runnable> testBody) {
        TestSpecDefinition createdSpec = TestSpecDefinition.create(testName, testBody, specTree.getSharedContext());
        stack.getCurrentGroup().addTest(createdSpec);
        return createdSpec;
    }


    @Override
    public void describe(String aGroupName, Runnable aGroupDefinition){
        nestGroupDefinition(aGroupName, aGroupDefinition);
    }

    @Override
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

    @Override
    public T context() {
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

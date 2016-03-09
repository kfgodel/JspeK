package ar.com.dgarcia.javaspec.impl;

import ar.com.dgarcia.javaspec.api.JavaSpecApi;
import ar.com.dgarcia.javaspec.api.contexts.ClassBasedTestContext;
import ar.com.dgarcia.javaspec.api.contexts.TestContext;
import ar.com.dgarcia.javaspec.api.exceptions.SpecException;
import ar.com.dgarcia.javaspec.api.variable.Variable;
import ar.com.dgarcia.javaspec.impl.context.typed.TypedContextFactory;
import ar.com.dgarcia.javaspec.impl.model.SpecGroup;
import ar.com.dgarcia.javaspec.impl.model.SpecTree;
import ar.com.dgarcia.javaspec.impl.model.impl.GroupSpecDefinition;
import ar.com.dgarcia.javaspec.impl.model.impl.TestSpecDefinition;
import ar.com.dgarcia.javaspec.impl.parser.SpecStack;

/**
 * This type represents the behavior of a javaspec instance to be used
 * as a component (by delegation) instead of inheritance.<br>
 *   Due to the way specs are defined for junit, it's easier for the user to extend Javaspec
 *   than use delegation. However the behavior for defining specs is extracted in this class so it
 *   can potentially play with other testing frameworks too, or be used programmatically
 *
 * Created by kfgodel on 09/03/16.
 */
public class SpecDescriber<T extends TestContext> implements JavaSpecApi<T> {

  private SpecStack stack;
  private SpecTree specTree;
  private T typedContext;

  public void beforeEach(Runnable aCodeBlock) {
    stack.getCurrentHead().addBeforeBlock(aCodeBlock);
  }

  @Override
  public void afterEach(Runnable aCodeBlock) {
    stack.getCurrentHead().addAfterBlock(aCodeBlock);
  }

  @Override
  public void it(String testName, Runnable aTestCode) {
    TestSpecDefinition createdSpec = TestSpecDefinition.create(testName, aTestCode, specTree.getSharedContext());
    stack.getCurrentHead().addTest(createdSpec);
  }

  @Override
  public void it(String testName) {
    TestSpecDefinition createdSpec = TestSpecDefinition.createPending(testName, specTree.getSharedContext());
    stack.getCurrentHead().addTest(createdSpec);
  }

  @Override
  public void xit(String testName, Runnable aTestCode) {
    TestSpecDefinition createdSpec = TestSpecDefinition.create(testName, aTestCode, specTree.getSharedContext());
    createdSpec.markAsPending();
    stack.getCurrentHead().addTest(createdSpec);
  }

  @Override
  public void describe(String aGroupName, Runnable aGroupDefinition) {
    createGroupDefinition(aGroupName, aGroupDefinition);
  }

  @Override
  public void describe(Class<?> aClass, Runnable aGroupDefinition){
    // Sanity check to verify correct usage
    if(!ClassBasedTestContext.class.isInstance(context())){
      throw new SpecException("#describe can't be called with a class if the test context is not a ClassBasedTestContext subtype");
    }
    // Junit likes to split the description if I use the full class name
    String groupName = "class: " + aClass.getSimpleName();
    describe(groupName, aGroupDefinition);
    ClassBasedTestContext classContext = (ClassBasedTestContext) context();
    classContext.describedClass(()-> aClass);
  }

  @Override
  public void xdescribe(String aGroupName, Runnable aGroupDefinition) {
    GroupSpecDefinition createdGroup = createGroupDefinition(aGroupName, aGroupDefinition);
    createdGroup.markAsDisabled();
  }

  private GroupSpecDefinition createGroupDefinition(String aGroupName, Runnable aGroupDefinition) {
    GroupSpecDefinition createdGroup = GroupSpecDefinition.create(aGroupName);
    stack.executeAsCurrent(createdGroup, aGroupDefinition);
    stack.getCurrentHead().addSubGroup(createdGroup);
    return createdGroup;
  }

  /**
   * Allows access to test context, to define variables or to access them.<br>
   * Usually you define variables in suites and access them in tests
   *
   * @return The current test context
   */
  public T context() {
    return typedContext;
  }

  /**
   * Creates a new spec describer  that will populate the branches of the given tree when its methods
   * are called
   * @param specTree The tree to collect the spec meta description
   * @param <T> The type of test context
   * @return The created describer
   */
  public static<T extends TestContext> SpecDescriber<T> create(SpecTree specTree, Class<T> expectedContextType) {
    SpecDescriber<T> describer = new SpecDescriber<>();
    describer.specTree = specTree;
    describer.initialize(expectedContextType);
    return describer;
  }

  /**
   * Initializes this instance with the stack and context to collect spec meta description
   * from the method calls
   * @param expectedContextType
   */
  private void initialize(Class<T> expectedContextType) {
    SpecGroup rootGroup = this.specTree.getRootGroup();
    Variable<TestContext> sharedContext = this.specTree.getSharedContext();
    this.stack = SpecStack.create(rootGroup, sharedContext);
    this.typedContext = TypedContextFactory.createInstanceOf(expectedContextType, sharedContext);
  }

}

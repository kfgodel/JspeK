package ar.com.dgarcia.javaspec.impl.modes;

import ar.com.dgarcia.javaspec.api.contexts.ClassBasedTestContext;
import ar.com.dgarcia.javaspec.api.contexts.TestContext;
import ar.com.dgarcia.javaspec.api.exceptions.FailingRunnable;
import ar.com.dgarcia.javaspec.api.exceptions.SpecException;
import ar.com.dgarcia.javaspec.api.variable.Let;
import ar.com.dgarcia.javaspec.impl.model.SpecGroup;
import ar.com.dgarcia.javaspec.impl.model.SpecTest;
import ar.com.dgarcia.javaspec.impl.model.SpecTree;
import ar.com.dgarcia.javaspec.impl.parser.SpecStack;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * This type represents the available api when the tests are being defined.<br>
 *   Through an instance of this class a spec tree can be populated by calling the
 *   user available methods to create a complete spec definition
 *
 * Created by kfgodel on 09/03/16.
 */
public class DefinitionMode<T extends TestContext> implements ApiMode<T> {

  private SpecStack stack;
  private SpecTree specTree;
  private T typedContext;
  private RunningMode<T> runningMode;

  public void beforeEach(Runnable aCodeBlock) {
    stack.getCurrentHead().addBeforeBlock(aCodeBlock);
  }

  @Override
  public void afterEach(Runnable aCodeBlock) {
    stack.getCurrentHead().addAfterBlock(aCodeBlock);
  }

  @Override
  public void it(String testName, Runnable aTestCode) {
    stack.getCurrentHead().createTest(testName, Optional.of(aTestCode), specTree.getSharedContext());
  }

  @Override
  public void xit(String testName) {
    SpecTest createdSpec = stack.getCurrentHead().createTest(testName, Optional.empty(), specTree.getSharedContext());
    createdSpec.markAsPending();
  }

  @Override
  public void xit(String testName, Runnable aTestCode) {
    SpecTest createdSpec = stack.getCurrentHead().createTest(testName, Optional.of(aTestCode), specTree.getSharedContext());
    createdSpec.markAsPending();
  }

  @Override
  public <X extends Throwable> void itThrows(Class<X> expectedExceptionType, String testNameSuffix, FailingRunnable<X> aTestCode, Consumer<X> exceptionAssertions) throws SpecException {
    String expectedTypeName = expectedExceptionType.getSimpleName();
    String testName = "throws " + expectedTypeName + " " +  testNameSuffix;
    Runnable testCode = ()->{
      try {
        aTestCode.run();
        throw new AssertionError("No exception thrown while expecting: " + expectedTypeName);
      }catch (AssertionError e){
        throw e;
      }catch (Throwable e){
        if(expectedExceptionType.isAssignableFrom(e.getClass())){
          exceptionAssertions.accept((X) e);
        }else{
          throw new AssertionError("Caught " + e + " while expecting " + expectedTypeName, e);
        }
      }
    };
    it(testName, testCode);
  }

  @Override
  public void describe(String aGroupName, Runnable aGroupDefinition) {
    createGroupDefinition(aGroupName, aGroupDefinition);
  }

  @Override
  public void xdescribe(String aGroupName, Runnable aGroupDefinition) {
    SpecGroup createdGroup = createGroupDefinition(aGroupName, aGroupDefinition);
    createdGroup.markAsDisabled();
  }

  @Override
  public void describe(Class<?> aClass, Runnable aGroupDefinition){
    createClassBasedGroupDescription(aClass, aGroupDefinition);
  }

  @Override
  public void xdescribe(Class<?> aClass, Runnable aGroupDefinition) {
    SpecGroup groupDefinition = createClassBasedGroupDescription(aClass, aGroupDefinition);
    groupDefinition.markAsDisabled();
  }

  /**
   * Creates the description of a class based test group
   * @param aClass The class to base the group on
   * @param aGroupDefinition The test definitions
   * @return The created group
   */
  private SpecGroup createClassBasedGroupDescription(Class<?> aClass, Runnable aGroupDefinition) {
    // Sanity check to verify correct usage
    if (!(context() instanceof ClassBasedTestContext)) {
      throw new SpecException("#describe can't be called with a class if the test context is not a ClassBasedTestContext subtype");
    }
    // Junit likes to split the description if I use the full class name
    String groupName = "class: " + aClass.getSimpleName();
    SpecGroup groupDefinition = createGroupDefinition(groupName, aGroupDefinition);
    ClassBasedTestContext classContext = (ClassBasedTestContext) groupDefinition.getTestContext();
    classContext.describedClass(()-> aClass);
    return groupDefinition;
  }

  private SpecGroup createGroupDefinition(String aGroupName, Runnable aGroupDefinition) {
    SpecGroup createdGroup = stack.getCurrentHead().createGroup(aGroupName);
    stack.executeAsCurrent(createdGroup, aGroupDefinition);
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

  @Override
  public <X> Let<X> localLet(String variableName) {
    return Let.create(variableName, this::context);
  }

  /**
   * Creates a new spec describer  that will populate the branches of the given tree when its methods
   * are called
   * @param initialMode The previous mode to start registering definitions
   * @param <T> The type of test context
   * @return The created describer
   */
  public static <T extends TestContext> DefinitionMode<T> create(InstantiationMode<T> initialMode) {
    DefinitionMode<T> describer = new DefinitionMode<>();
    describer.specTree = initialMode.getTree();
    describer.typedContext = initialMode.context();
    describer.initialize();
    return describer;
  }

  /**
   * Initializes this instance with the stack and context to collect spec meta description
   * from the method calls
   */
  private void initialize() {
    this.runningMode = RunningMode.create(this.context(), this.specTree);
    this.stack = this.specTree.createStack();
  }

  @Override
  public ApiMode<T> changeToDefinition() {
    throw new SpecException("A definition mode can't change into definition again");
  }

  /**
   * Creates a running mode based on the current definitions for this instance
   * @return The running version of this mode
   */
  public RunningMode<T> changeToRunning() {
    return runningMode;
  }

  @Override
  public SpecTree getTree() {
    return specTree;
  }
}

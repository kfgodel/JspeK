package info.kfgodel.jspek.impl.modes;

import info.kfgodel.jspek.api.contexts.ClassBasedTestContext;
import info.kfgodel.jspek.api.contexts.TestContext;
import info.kfgodel.jspek.api.exceptions.FailingRunnable;
import info.kfgodel.jspek.api.exceptions.SpecException;
import info.kfgodel.jspek.api.variable.Let;
import info.kfgodel.jspek.api.variable.Variable;
import info.kfgodel.jspek.impl.context.typed.TypedContextFactory;
import info.kfgodel.jspek.impl.model.SpecGroup;
import info.kfgodel.jspek.impl.model.SpecTree;
import info.kfgodel.jspek.impl.model.impl.GroupSpecDefinition;
import info.kfgodel.jspek.impl.model.impl.TestSpecDefinition;
import info.kfgodel.jspek.impl.parser.SpecStack;

import java.util.function.Consumer;

/**
 * This type represents the available api when the tests are being defined.<br>
 * Through an instance of this class a spec tree can be populated by calling the
 * user available methods to create a complete spec definition
 * <p>
 * Created by kfgodel on 09/03/16.
 */
public class DefinitionMode<T extends TestContext> implements ExecutionMode<T> {

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
    TestSpecDefinition createdSpec = TestSpecDefinition.create(testName, aTestCode, specTree.getSharedContext());
    stack.getCurrentHead().addTest(createdSpec);
  }

  @Override
  public void xit(String testName) {
    TestSpecDefinition createdSpec = TestSpecDefinition.createPending(testName, specTree.getSharedContext());
    stack.getCurrentHead().addTest(createdSpec);
  }

  @Override
  public void xit(String testName, Runnable aTestCode) {
    TestSpecDefinition createdSpec = TestSpecDefinition.create(testName, aTestCode, specTree.getSharedContext());
    createdSpec.markAsPending();
    stack.getCurrentHead().addTest(createdSpec);
  }

  @SuppressWarnings("unchecked")
  // X is used to parameterize the type of exception returned, but in runtime that type argument can't be used
  @Override
  public <X extends Throwable> void itThrows(
    Class<X> expectedExceptionType,
    String testNameSuffix,
    FailingRunnable<? extends X> aFailingTestCode,
    Consumer<? super X> exceptionAssertions
  ) throws SpecException {
    String expectedTypeName = expectedExceptionType.getSimpleName();
    String testName = "throws " + expectedTypeName + " " + testNameSuffix;
    Runnable testCodeWrapper = () -> tryCatchForExpectedError(aFailingTestCode, expectedExceptionType,
      exceptionAssertions, expectedTypeName);
    it(testName, testCodeWrapper);
  }

  public static <X extends Throwable> void tryCatchForExpectedError(
    FailingRunnable<? extends X> aFailingTestCode,
    Class<X> expectedExceptionType,
    Consumer<? super X> exceptionAssertions,
    String expectedTypeName
  ) {
    try {
      aFailingTestCode.run();
      throw new AssertionError("No exception thrown while expecting: " + expectedTypeName);
    } catch (AssertionError e) {
      throw e;
    } catch (Throwable e) { // NOSONAR squid:S1181 Errors may be part of the tested code and an expected throwable
      if (expectedExceptionType.isAssignableFrom(e.getClass())) {
        exceptionAssertions.accept((X) e);
      } else {
        throw new AssertionError("Caught " + e + " while expecting " + expectedTypeName, e);
      }
    }
  }

  @Override
  public void describe(String aGroupName, Runnable aGroupDefinition) {
    createGroupDefinition(aGroupName, aGroupDefinition);
  }

  @Override
  public void xdescribe(String aGroupName, Runnable aGroupDefinition) {
    GroupSpecDefinition createdGroup = createGroupDefinition(aGroupName, aGroupDefinition);
    createdGroup.markAsDisabled();
  }

  @Override
  public void describe(Class<?> aClass, Runnable aGroupDefinition) {
    createClassBasedGroupDescription(aClass, aGroupDefinition);
  }

  @Override
  public void xdescribe(Class<?> aClass, Runnable aGroupDefinition) {
    GroupSpecDefinition groupDefinition = createClassBasedGroupDescription(aClass, aGroupDefinition);
    groupDefinition.markAsDisabled();
  }

  /**
   * Creates the description of a class based test group
   *
   * @param aClass           The class to base the group on
   * @param aGroupDefinition The test definitions
   * @return The created group
   */
  @SuppressWarnings("unchecked")
  // We use the class parameter without being able to check if it fits as describedclass (not possible wo generics on runtime)
  private GroupSpecDefinition createClassBasedGroupDescription(Class<?> aClass, Runnable aGroupDefinition) {
    // Sanity check to verify correct usage
    if (!(context() instanceof ClassBasedTestContext)) {
      throw new SpecException("#describe can't be called with a class if the test context is not a ClassBasedTestContext subtype");
    }
    // Junit likes to split the description if I use the full class name
    String groupName = "class: " + aClass.getSimpleName();
    GroupSpecDefinition groupDefinition = createGroupDefinition(groupName, aGroupDefinition);
    ClassBasedTestContext classContext = (ClassBasedTestContext) groupDefinition.getTestContext();
    classContext.describedClass(() -> aClass);
    return groupDefinition;
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

  @Override
  public <X> Let<X> localLet(String variableName) {
    return Let.create(variableName, this::context);
  }

  /**
   * /**
   * Creates a new spec describer  that will populate the branches of the given tree when its methods
   * are called
   *
   * @param specTree The tree to collect the spec meta description
   * @param <T>      The type of test context
   * @return The created describer
   */
  public static <T extends TestContext> DefinitionMode<T> create(SpecTree specTree, Class<T> expectedContextType) {
    DefinitionMode<T> describer = new DefinitionMode<>();
    describer.specTree = specTree;
    describer.initialize(expectedContextType);
    return describer;
  }

  /**
   * Initializes this instance with the stack and context to collect spec meta description
   * from the method calls
   *
   * @param expectedContextType
   */
  private void initialize(Class<T> expectedContextType) {
    SpecGroup rootGroup = this.specTree.getRootGroup();
    Variable<TestContext> sharedContext = this.specTree.getSharedContext();
    this.stack = SpecStack.create(rootGroup, sharedContext);
    this.typedContext = TypedContextFactory.createInstanceOf(expectedContextType, sharedContext);
    this.runningMode = RunningMode.create(this.context());
  }

  /**
   * Creates a running mode based on the current definitions for this instance
   *
   * @return The running version of this mode
   */
  public RunningMode<T> changeToRunning() {
    return runningMode;
  }
}

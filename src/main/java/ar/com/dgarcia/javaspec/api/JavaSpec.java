package ar.com.dgarcia.javaspec.api;

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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * This class is the extension point to add testing expressiveness with Java Specs.<br>
 * The method idiom is copied from: http://jasmine.github.io/2.0/introduction.html.<br>
 * Created by kfgodel on 12/07/14.
 */
public abstract class JavaSpec<T extends TestContext> implements JavaSpecApi<T> {

  private SpecTree specTree;
  private SpecStack stack;
  private T typedContext;

  /**
   * Starting method to define the specs.<br>
   * This method must be extended by subclasses and define any spec as calls to describe() and it()
   */
  public abstract void define();

  @Override
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
   * Uses the definition of this spec class to create the nodes in the tree.<br>
   * The defined tree must be validated before using it
   *
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
   * Usually you define variables in suites and access them in tests
   *
   * @return The current test context
   */
  protected T context() {
    return typedContext;
  }

  public Class<T> getContextTypeFromSubclassDeclaration() {
    Class<? extends JavaSpec> subClass = getClass();
    if (!(subClass.getSuperclass().equals(JavaSpec.class))) {
      throw new SpecException("A java spec class[" + subClass + "] must inherit directly from " + JavaSpec.class);
    }
    Type generifiedJavaSpec = subClass.getGenericSuperclass();
    if (!(generifiedJavaSpec instanceof ParameterizedType)) {
      throw new SpecException("JavaSpec superclass must be generified with a type of TestContext. For example JavaSpec<TestContext>");
    }
    Type contextType = ((ParameterizedType) generifiedJavaSpec).getActualTypeArguments()[0];
    return getClassInstanceFromArgument(contextType);
  }

  /**
   * Tries toget the raw class instance that represents the interface defining the test context
   * @param contextType The type that is found on the spec declaration
   * @return The class instance that represents the test context interface
   */
  private Class<T> getClassInstanceFromArgument(Type contextType) throws SpecException {
    if (contextType instanceof ParameterizedType) {
      // It's another generic type, it may be a class based context
      ParameterizedType parameterizedArgument = (ParameterizedType) contextType;
      Type rawType = parameterizedArgument.getRawType();
      if (!Class.class.isInstance(rawType)) {
        throw new SpecException("JavaSpec parameterization can't reference a generic type: " + rawType);
      }
      return (Class<T>) rawType;
    }
    if (!Class.class.isInstance(contextType)) {
      throw new SpecException("JavaSpec generic parameter can't be a type wildcard: " + contextType);
    }
    return (Class<T>) contextType;
  }
}

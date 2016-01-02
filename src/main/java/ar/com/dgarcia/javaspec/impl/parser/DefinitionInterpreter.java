package ar.com.dgarcia.javaspec.impl.parser;

import ar.com.dgarcia.javaspec.api.*;
import ar.com.dgarcia.javaspec.impl.context.typed.TypedContextFactory;
import ar.com.dgarcia.javaspec.impl.exceptions.SpecException;
import ar.com.dgarcia.javaspec.impl.model.SpecGroup;
import ar.com.dgarcia.javaspec.impl.model.SpecTree;
import ar.com.dgarcia.javaspec.impl.model.impl.GroupSpecDefinition;
import ar.com.dgarcia.javaspec.impl.model.impl.SpecTreeDefinition;
import ar.com.dgarcia.javaspec.impl.model.impl.TestSpecDefinition;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

/**
 * This type represents the interpreter that builds the spec tree when api methods are called,
 * interpreting the users input as test specificiations
 *
 * Created by tenpines on 31/12/15.
 */
public class DefinitionInterpreter<T extends TestContext> implements JavaSpecApi<T> {

  private SpecTreeDefinition specTree;
  private Variable<TestContext> sharedContext;
  private SpecStack stack;
  private T typedContext;
  private Class<?> specClass;

  public static<T extends TestContext> DefinitionInterpreter<T> create(Class<?> specClass){
    DefinitionInterpreter<T> interpreter = new DefinitionInterpreter<>();
    interpreter.specClass = specClass;
    interpreter.initialize();
    return interpreter;
  }

  private void initialize() {
    this.specTree =  SpecTreeDefinition.create(specClass);

    this.sharedContext = Variable.create();
    SpecGroup rootGroup = specTree.getRootGroup();
    this.stack = SpecStack.create(rootGroup, sharedContext);

    Class<T> expectedContextType = this.getContextTypeFromClassDeclaration();
    this.typedContext = TypedContextFactory.createInstanceOf(expectedContextType, sharedContext);
  }

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
    TestSpecDefinition createdSpec = TestSpecDefinition.create(testName, testBody, sharedContext);
    stack.getCurrentGroup().addSubElement(createdSpec);
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

  @Override
  public T context() {
    return typedContext;
  }

  public SpecTree getSpecTree() {
    return specTree;
  }

  public Class<T> getContextTypeFromClassDeclaration() {
    Type generifiedJavaSpec = getGenerifiedType();
    if(!(generifiedJavaSpec instanceof ParameterizedType)){
      throw new SpecException("JavaSpec class must be generified with a type of TestContext. For example JavaSpec<TestContext>");
    }
    Type contextType = ((ParameterizedType) generifiedJavaSpec).getActualTypeArguments()[0];
    if(!(contextType instanceof Class)){
      throw new SpecException("JavaSpec parameter is not a class?");
    }
    return (Class<T>) contextType;
  }

  public Type getGenerifiedType() {
    if(specClass.getSuperclass().equals(JavaSpec.class)){
      // It's generic parameter is defined in the superclass declaration
      return specClass.getGenericSuperclass();
    }
    // If not by inheritance, then by implementation
    Optional<Type> implementedInterface = getGenerifiedInterface();
    return implementedInterface.orElseThrow(()-> new SpecException("JavaSpec interface must be generified with a type of TestContext. For example JavaSpecTestablce<TestContext>"));
  }

  public Optional<Type> getGenerifiedInterface() {
    Type[] genericInterfaces = specClass.getGenericInterfaces();
    Class<?>[] rawInterfaces = specClass.getInterfaces();
    for (int i = 0; i < rawInterfaces.length; i++) {
      Class<?> aRawInterface = rawInterfaces[i];
      if(aRawInterface.equals(JavaSpecTestable.class)){
        return Optional.of(genericInterfaces[i]);
      }
    }
    return Optional.empty();
  }
}

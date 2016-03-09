package ar.com.dgarcia.javaspec.api;

import ar.com.dgarcia.javaspec.api.contexts.TestContext;
import ar.com.dgarcia.javaspec.api.exceptions.SpecException;
import ar.com.dgarcia.javaspec.impl.RunningMode;
import ar.com.dgarcia.javaspec.impl.DefinitionMode;
import ar.com.dgarcia.javaspec.impl.model.SpecTree;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * This class is the extension point to add testing expressiveness with Java Specs.<br>
 * The method idiom is copied from: http://jasmine.github.io/2.0/introduction.html.<br>
 * Created by kfgodel on 12/07/14.
 */
public abstract class JavaSpec<T extends TestContext> implements JavaSpecApi<T> {

  private JavaSpecApi<T> currentMode;

  /**
   * Starting method to define the specs.<br>
   * This method must be extended by subclasses and define any spec as calls to describe() and it()
   */
  public abstract void define();

  /**
   * Uses the user definition of this spec class (contained in the define() method) to create
   * the nodes in the given tree.<br>
   *
   * @param specTree The tree that will represent this spec
   */
  public void populate(SpecTree specTree) {
    this.currentMode =  DefinitionMode.create(specTree, getContextTypeFromSubclassDeclaration());
    this.define();
    // Switch to execution mode to prepare for test to be run
    this.currentMode = RunningMode.create(this.currentMode.context());
  }

  /**
   * Using reflection gets the concrete type used to parameterize this class.<br>
   * That parameter is going to be proxied later and made available to the user
   * @return The concrete test context subtype or an exception if pre-conditions are not met
   */
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


  // **************************************************
  //  Any API call delegate it to the api to build the meta tree for the spec description
  //  or execute running test behavior
  // **************************************************

  @Override
  public T context() {
    return currentMode.context();
  }

  @Override
  public void xdescribe(String aGroupName, Runnable aGroupDefinition) {
    currentMode.xdescribe(aGroupName, aGroupDefinition);
  }

  @Override
  public void xdescribe(Class<?> aGroupName, Runnable aGroupDefinition) {
    currentMode.xdescribe(aGroupName, aGroupDefinition);
  }

  @Override
  public void describe(Class<?> aClass, Runnable aGroupDefinition) {
    currentMode.describe(aClass, aGroupDefinition);
  }

  @Override
  public void describe(String aGroupName, Runnable aGroupDefinition) {
    currentMode.describe(aGroupName, aGroupDefinition);
  }

  @Override
  public void xit(String testName, Runnable aTestCode) {
    currentMode.xit(testName, aTestCode);
  }

  @Override
  public void it(String testName) {
    currentMode.it(testName);
  }

  @Override
  public void it(String testName, Runnable aTestCode) {
    currentMode.it(testName, aTestCode);
  }

  @Override
  public void afterEach(Runnable aCodeBlock) {
    currentMode.afterEach(aCodeBlock);
  }

  @Override
  public void beforeEach(Runnable aCodeBlock) {
    currentMode.beforeEach(aCodeBlock);
  }
}

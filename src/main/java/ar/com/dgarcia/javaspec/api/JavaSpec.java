package ar.com.dgarcia.javaspec.api;

import ar.com.dgarcia.javaspec.impl.model.SpecTree;
import ar.com.dgarcia.javaspec.impl.parser.SpecInterpreter;

/**
 * This class is the extension point to add testing expressiveness with Java Specs.<br>
 * The method idiom is copied from: http://jasmine.github.io/2.0/introduction.html.<br>
 * Created by kfgodel on 12/07/14.
 */
public abstract class JavaSpec<T extends TestContext> implements JavaSpecApi<T> {

  private SpecInterpreter<T> interpreter;

  /**
   * Starting method to define the specs.<br>
   * This method must be extended by subclasses and define any spec as calls to describe() and it()
   */
  public abstract void define();

  /**
   * Creates a spec definition tree with the specification defined by the user in the subclass
   */
  public SpecTree defineTree() {
    // Needs to be instance variable to be accesed in the define method
    this.interpreter = SpecInterpreter.create(this.getClass());

    this.define();

    return interpreter.getSpecTree();
  }

  @Override
  public T context() {
    return interpreter.context();
  }

  @Override
  public void beforeEach(Runnable aCodeBlock) {
    interpreter.beforeEach(aCodeBlock);
  }

  @Override
  public void afterEach(Runnable aCodeBlock) {
    interpreter.afterEach(aCodeBlock);
  }

  @Override
  public void it(String testName, Runnable aTestCode) {
    interpreter.it(testName, aTestCode);
  }

  @Override
  public void it(String testName) {
    interpreter.it(testName);
  }

  @Override
  public void xit(String testName, Runnable aTestCode) {
    interpreter.xit(testName, aTestCode);
  }

  @Override
  public void describe(String aGroupName, Runnable aGroupDefinition) {
    interpreter.describe(aGroupName, aGroupDefinition);
  }

  @Override
  public void xdescribe(String aGroupName, Runnable aGroupDefinition) {
    interpreter.xdescribe(aGroupName, aGroupDefinition);
  }
}

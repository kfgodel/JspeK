package ar.com.dgarcia.javaspec.impl.parser;

import ar.com.dgarcia.javaspec.api.JavaSpecApi;
import ar.com.dgarcia.javaspec.api.TestContext;
import ar.com.dgarcia.javaspec.impl.exceptions.SpecException;

/**
 * This type represents the interpreter that rejects user actions trying to define spec after
 * the tree has been already parsed.
 *
 * Created by tenpines on 31/12/15.
 */
public class ExecutionInterpreter<T extends TestContext> implements JavaSpecApi<T> {
  private DefinitionInterpreter<T> definitionInterpreter;

  @Override
  public void beforeEach(Runnable aCodeBlock) {
    throw new SpecException("Cannot call beforeEach during the execution of a test");
  }

  @Override
  public void afterEach(Runnable aCodeBlock) {
    throw new SpecException("Cannot call afterEach during the execution of a test");
  }

  @Override
  public void it(String testName, Runnable aTestCode) {
    throw new SpecException("Cannot call it(\""+testName+"\") during the execution of a test");
  }

  @Override
  public void it(String testName) {
    throw new SpecException("Cannot call it(\""+testName+"\") during the execution of a test");
  }

  @Override
  public void xit(String testName, Runnable aTestCode) {
    throw new SpecException("Cannot call xit(\""+testName+"\") during the execution of a test");
  }

  @Override
  public void describe(String aGroupName, Runnable aGroupDefinition) {
    throw new SpecException("Cannot call describe(\""+aGroupName+"\") during the execution of a test");
  }

  @Override
  public void xdescribe(String aGroupName, Runnable aGroupDefinition) {
    throw new SpecException("Cannot call xdescribe(\""+aGroupName+"\") during the execution of a test");
  }

  @Override
  public T context() {
    return definitionInterpreter.context();
  }

  public static<T extends TestContext> ExecutionInterpreter<T> create(DefinitionInterpreter<T> definitionInterpreter){
    ExecutionInterpreter<T> executionInterpreter = new ExecutionInterpreter<>();
    executionInterpreter.definitionInterpreter = definitionInterpreter;
    return executionInterpreter;
  }
}

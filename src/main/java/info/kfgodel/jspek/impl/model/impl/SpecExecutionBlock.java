package info.kfgodel.jspek.impl.model.impl;

import info.kfgodel.jspek.api.contexts.TestContext;
import info.kfgodel.jspek.api.variable.Variable;
import info.kfgodel.jspek.impl.context.MappedContext;
import info.kfgodel.jspek.impl.model.TestContextDefinition;

import java.util.List;

/**
 * This type represents a test to be run as a code block.<br>
 * By this class we abstract the spec execution to Junit
 * Created by kfgodel on 13/07/14.
 */
public class SpecExecutionBlock implements Runnable {

  private Variable<TestContext> sharedContext;
  private TestContextDefinition parentContext;
  private List<Runnable> beforeBlocks;
  private Runnable testCode;
  private List<Runnable> afterBlocks;

  @Override
  public void run() {
    runWithOwnSubContext(() -> executeTestCode());
  }

  /**
   * Creates a new context to run given code, restoring shared context to previous value after execution
   *
   * @param codeToRun Code to run in own context
   */
  private void runWithOwnSubContext(Runnable codeToRun) {
    MappedContext testRunContext = MappedContext.create();
    testRunContext.setParentDefinition(parentContext);

    TestContext previousContext = sharedContext.get();
    sharedContext.set(testRunContext);
    try {
      codeToRun.run();
    } finally {
      sharedContext.set(previousContext);
    }
  }

  private void executeTestCode() {
    executeBlocks(beforeBlocks);
    try {
      testCode.run();
    } finally {
      executeBlocks(afterBlocks);
    }
  }

  private void executeBlocks(List<Runnable> runnableBlocks) {
    for (Runnable runnable : runnableBlocks) {
      runnable.run();
    }
  }

  public static SpecExecutionBlock create(List<Runnable> befores, Runnable testCode, List<Runnable> afters, TestContextDefinition parentContext, Variable<TestContext> sharedContext) {
    SpecExecutionBlock executionBlock = new SpecExecutionBlock();
    executionBlock.testCode = testCode;
    executionBlock.afterBlocks = afters;
    executionBlock.beforeBlocks = befores;
    executionBlock.sharedContext = sharedContext;
    executionBlock.parentContext = parentContext;
    return executionBlock;
  }
}

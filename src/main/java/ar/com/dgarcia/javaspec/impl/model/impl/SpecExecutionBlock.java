package ar.com.dgarcia.javaspec.impl.model.impl;

import ar.com.dgarcia.javaspec.api.TestContext;
import ar.com.dgarcia.javaspec.api.Variable;
import ar.com.dgarcia.javaspec.impl.context.MappedContext;
import ar.com.dgarcia.javaspec.impl.model.TestContextDefinition;

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
    private Runnable testBlock;
    private List<Runnable> afterBlocks;

    @Override
    public void run() {
        runWithOwnSubContext(this::executeTestCode);
    }

    /**
     * Creates a new context to run given code, restoring shared context to previous value after execution
     * @param codeToRun Code to run in own context
     */
    private void runWithOwnSubContext(Runnable codeToRun) {
        MappedContext testRunContext = MappedContext.createWithParent(parentContext);

        TestContext previousContext = sharedContext.get();
        sharedContext.set(testRunContext);
        try{
            codeToRun.run();
        }finally{
            sharedContext.set(previousContext);
        }
    }

    private void executeTestCode() {
        for (Runnable beforeBlock : beforeBlocks) {
            beforeBlock.run();
        }
        testBlock.run();
        for (Runnable afterBlock : afterBlocks) {
            afterBlock.run();
        }
    }

    public static SpecExecutionBlock create(List<Runnable> befores, Runnable testBlock, List<Runnable> afters, TestContextDefinition parentContext, Variable<TestContext> sharedContext) {
        SpecExecutionBlock executionBlock = new SpecExecutionBlock();
        executionBlock.testBlock = testBlock;
        executionBlock.afterBlocks = afters;
        executionBlock.beforeBlocks = befores;
        executionBlock.sharedContext = sharedContext;
        executionBlock.parentContext = parentContext;
        return executionBlock;
    }
}

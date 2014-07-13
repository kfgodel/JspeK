package ar.com.dgarcia.javaspec.impl.model.impl;

import java.util.List;

/**
 * Created by kfgodel on 13/07/14.
 */
public class SpecExecutionBlock implements Runnable {

    private List<Runnable> beforeBlocks;
    private Runnable testCode;
    private List<Runnable> afterBlocks;

    @Override
    public void run() {
        for (Runnable beforeBlock : beforeBlocks) {
            beforeBlock.run();
        }
        testCode.run();
        for (Runnable afterBlock : afterBlocks) {
            afterBlock.run();
        }
    }

    public static SpecExecutionBlock create(List<Runnable> befores, Runnable testCode, List<Runnable> afters) {
        SpecExecutionBlock executionBlock = new SpecExecutionBlock();
        executionBlock.testCode = testCode;
        executionBlock.afterBlocks = afters;
        executionBlock.beforeBlocks = befores;
        return executionBlock;
    }
}

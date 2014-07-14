package ar.com.dgarcia.javaspec.impl.model.impl;

import java.util.List;

import ar.com.dgarcia.javaspec.impl.model.SpecTest;

/**
 * This type represents the interpreted definition of a test spec
 * Created by kfgodel on 12/07/14.
 */
public class TestSpecDefinition extends SpecElementSupport implements SpecTest {

    private Runnable testCode;
    private PendingStatus pendingState;

    @Override
    public boolean isMarkedAsPending() {
        return pendingState.isPendingConsidering(getContainerGroup()) || getContainerGroup().isMarkedAsDisabled();
    }

    @Override
    public List<Runnable> getBeforeBlocks() {
        return getContainerGroup().getBeforeBlocks();
    }

    @Override
    public List<Runnable> getAfterBlocks() {
        return getContainerGroup().getAfterBlocks();
    }

    @Override
    public Runnable getTestCode() {
        return testCode;
    }

    @Override
    public void markAsPending() {
        this.pendingState = PendingStatus.PENDING;
    }

    @Override
    public Runnable getSpecExecutionCode() {
        SpecExecutionBlock executionBlock = SpecExecutionBlock.create(this.getBeforeBlocks(), this.getTestCode(), this.getAfterBlocks());
        return executionBlock;
    }

    public static TestSpecDefinition create(String testName, Runnable testCode) {
        TestSpecDefinition test = new TestSpecDefinition();
        test.setName(testName);
        test.testCode = testCode;
        test.pendingState = PendingStatus.NORMAL;
        return test;
    }

    /**
     * Creates a test in pending state
     * @param testName The
     * @return
     */
    public static TestSpecDefinition createPending(String testName) {
        TestSpecDefinition test = create(testName, null);
        test.markAsPending();
        return test;
    }
}

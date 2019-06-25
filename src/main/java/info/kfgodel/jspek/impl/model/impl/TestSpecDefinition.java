package info.kfgodel.jspek.impl.model.impl;

import info.kfgodel.jspek.api.contexts.TestContext;
import info.kfgodel.jspek.api.variable.Variable;
import info.kfgodel.jspek.impl.model.SpecTest;

import java.util.List;

/**
 * This type represents the interpreted definition of a test spec
 * Created by kfgodel on 12/07/14.
 */
public class TestSpecDefinition extends SpecElementSupport implements SpecTest {

    private Runnable testCode;
    private PendingStatus pendingState;
    private Variable<TestContext> sharedContext;

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
        SpecExecutionBlock executionBlock = SpecExecutionBlock.create(this.getBeforeBlocks(), this.getTestCode(), this.getAfterBlocks(), getContainerGroup().getTestContext(), sharedContext);
        return executionBlock;
    }

    public static TestSpecDefinition create(String testName, Runnable testCode, Variable<TestContext> sharedContext) {
        TestSpecDefinition test = new TestSpecDefinition();
        test.setName(testName);
        test.testCode = testCode;
        test.pendingState = PendingStatus.NORMAL;
        test.sharedContext = sharedContext;
        return test;
    }

    /**
     * Creates a test in pending state
     * @param testName The name to identify the test
     * @return The created definition
     */
    public static TestSpecDefinition createPending(String testName, Variable<TestContext> sharedContext) {
        TestSpecDefinition test = create(testName, null, sharedContext);
        test.markAsPending();
        return test;
    }
}

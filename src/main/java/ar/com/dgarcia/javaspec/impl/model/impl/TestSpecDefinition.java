package ar.com.dgarcia.javaspec.impl.model.impl;

import ar.com.dgarcia.javaspec.api.TestContext;
import ar.com.dgarcia.javaspec.api.Variable;
import ar.com.dgarcia.javaspec.impl.model.SpecTest;

import java.util.List;
import java.util.Optional;

/**
 * This type represents the interpreted definition of a test spec
 * Created by kfgodel on 12/07/14.
 */
public class TestSpecDefinition extends SpecElementSupport implements SpecTest {

    private Optional<Runnable> testCode;
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
    public Optional<Runnable> getTestBodyCode() {
        return testCode;
    }

    @Override
    public void markAsPending() {
        this.pendingState = PendingStatus.PENDING;
    }

    @Override
    public Optional<Runnable> getFullExecutionCode() {
        if(isMarkedAsPending()){
            return Optional.empty();
        }
        return getTestBodyCode()
          .map((testBlock) -> SpecExecutionBlock.create(this.getBeforeBlocks(), testBlock, this.getAfterBlocks(), getContainerGroup().getTestContext(), sharedContext));
    }

    /**
     * Creates a test in pending state
     * @param testName The name to identify the test
     * @return The created definition
     */
    public static TestSpecDefinition create(String testName, Optional<Runnable> testCode, Variable<TestContext> sharedContext) {
        TestSpecDefinition test = new TestSpecDefinition();
        test.setName(testName);
        test.testCode = testCode;
        // State will be pending if there's no code to run as test
        test.pendingState = testCode.map((code)-> PendingStatus.NORMAL).orElse(PendingStatus.PENDING);
        test.sharedContext = sharedContext;
        return test;
    }
}

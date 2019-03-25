package ar.com.dgarcia.javaspec.impl.model.impl;

import ar.com.dgarcia.javaspec.api.contexts.TestContext;
import ar.com.dgarcia.javaspec.api.variable.Variable;
import ar.com.dgarcia.javaspec.impl.model.SpecTest;

import java.util.List;
import java.util.Optional;

/**
 * This type represents the interpreted definition of a test spec
 * Created by kfgodel on 12/07/14.
 */
public class SpecTestDefinition extends SpecElementSupport implements SpecTest {

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
    public Optional<Runnable> getTestCode() {
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

    public static SpecTestDefinition create(String testName, Optional<Runnable> testCode, Variable<TestContext> sharedContext, SpecGroupDefinition containerGroup) {
        SpecTestDefinition test = new SpecTestDefinition();
        test.setName(testName);
        test.testCode = testCode;
        test.pendingState = PendingStatus.NORMAL;
        test.sharedContext = sharedContext;
        test.setContainerGroup(containerGroup);
        return test;
    }

}

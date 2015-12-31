package ar.com.dgarcia.javaspec.impl.model.impl;

import ar.com.dgarcia.javaspec.impl.context.NullContextDefinition;
import ar.com.dgarcia.javaspec.impl.model.SpecElement;
import ar.com.dgarcia.javaspec.impl.model.SpecGroup;
import ar.com.dgarcia.javaspec.impl.model.SpecTest;
import ar.com.dgarcia.javaspec.impl.model.TestContextDefinition;

import java.util.Collections;
import java.util.List;

/**
 * This type represents a null group to be used as root container
 * Created by kfgodel on 12/07/14.
 */
public class NullContainerGroup implements SpecGroup {

    private TestContextDefinition testContext;

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public List<SpecGroup> getSubGroups() {
        throw new UnsupportedOperationException("Null container doesn't have subgroups");
    }

    @Override
    public String getName() {
        return "null container";
    }

    @Override
    public List<Runnable> getBeforeBlocks() {
        return Collections.emptyList();
    }

    @Override
    public List<Runnable> getAfterBlocks() {
        return Collections.emptyList();
    }

    @Override
    public List<SpecTest> getDeclaredTests() {
        throw new UnsupportedOperationException("Null container doesn't have subgroups");
    }

    @Override
    public boolean isMarkedAsDisabled() {
        return false;
    }

    @Override
    public void markAsDisabled() {
        throw new UnsupportedOperationException("Null container cannot be disabled");
    }

    @Override
    public void addSubElement(SpecElement subElement) {
        throw new UnsupportedOperationException("Null container cannot have sub elements");
    }

    @Override
    public void addBeforeBlock(Runnable aCodeBlock) {
        throw new UnsupportedOperationException("Null container cannot have before block");
    }

    @Override
    public void addAfterBlock(Runnable aCodeBlock) {
        throw new UnsupportedOperationException("Null container cannot have after block");
    }

    @Override
    public List<SpecElement> getSpecElements() {
        return Collections.emptyList();
    }

    @Override
    public boolean hasNoTests() {
        return true;
    }

    @Override
    public TestContextDefinition getTestContext() {
        return testContext;
    }

    public static NullContainerGroup create() {
        NullContainerGroup nullContainerGroup = new NullContainerGroup();
        nullContainerGroup.testContext = NullContextDefinition.create();
        return nullContainerGroup;
    }
}

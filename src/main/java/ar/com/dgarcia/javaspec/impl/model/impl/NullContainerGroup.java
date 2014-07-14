package ar.com.dgarcia.javaspec.impl.model.impl;

import ar.com.dgarcia.javaspec.impl.model.SpecElement;
import ar.com.dgarcia.javaspec.impl.model.SpecGroup;
import ar.com.dgarcia.javaspec.impl.model.SpecTest;

import java.util.Collections;
import java.util.List;

/**
 * Created by kfgodel on 12/07/14.
 */
public class NullContainerGroup implements SpecGroup {

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
    public void addSubGroup(GroupSpecDefinition addedGroup) {
        throw new UnsupportedOperationException("Null container cannot have subgroup");
    }

    @Override
    public void addTest(TestSpecDefinition addedSpec) {
        throw new UnsupportedOperationException("Null container cannot have test");
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

    public static NullContainerGroup create() {
        NullContainerGroup nullContainerGroup = new NullContainerGroup();
        return nullContainerGroup;
    }
}

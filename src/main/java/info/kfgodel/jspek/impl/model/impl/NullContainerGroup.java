package info.kfgodel.jspek.impl.model.impl;

import info.kfgodel.jspek.impl.context.NullContextDefinition;
import info.kfgodel.jspek.impl.model.SpecElement;
import info.kfgodel.jspek.impl.model.SpecGroup;
import info.kfgodel.jspek.impl.model.SpecTest;
import info.kfgodel.jspek.impl.model.TestContextDefinition;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

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
  public Stream<SpecElement> getSpecElements() {
    return Stream.empty();
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

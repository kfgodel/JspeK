package ar.com.dgarcia.javaspec.impl.model.impl;

import ar.com.dgarcia.javaspec.api.contexts.TestContext;
import ar.com.dgarcia.javaspec.api.variable.Variable;
import ar.com.dgarcia.javaspec.impl.context.NullContextDefinition;
import ar.com.dgarcia.javaspec.impl.model.SpecElement;
import ar.com.dgarcia.javaspec.impl.model.SpecGroup;
import ar.com.dgarcia.javaspec.impl.model.SpecTest;
import ar.com.dgarcia.javaspec.impl.model.TestContextDefinition;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * This type represents the the container group for those groups that  have no container.<br>
 * This class avoids using null by defining sensible default behavior for group methods when there's no
 * parent container of a group
 * Created by kfgodel on 12/07/14.
 */
public class AbsentGroup implements SpecGroup {

  private TestContextDefinition testContext;

  @Override
  public boolean isEmpty() {
    return true;
  }

  @Override
  public List<SpecGroup> getSubGroups() {
    throw new UnsupportedOperationException("Absent group doesn't have subgroups");
  }

  @Override
  public String getName() {
    return "Absent group";
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
    throw new UnsupportedOperationException("Absent group doesn't have subgroups");
  }

  @Override
  public boolean isMarkedAsDisabled() {
    return false;
  }

  @Override
  public void markAsDisabled() {
    throw new UnsupportedOperationException("Absent group cannot be disabled");
  }

  @Override
  public SpecGroup createGroup(String aGroupName) {
    throw new UnsupportedOperationException("Absent group cannot create a subgroup: " + aGroupName);
  }

  @Override
  public SpecTest createTest(String testName, Optional<Runnable> testCode, Variable<TestContext> sharedContext) {
    throw new UnsupportedOperationException("Absent group cannot create a test: " + testName);
  }

  @Override
  public void addTest(SpecTestDefinition addedSpec) {
    throw new UnsupportedOperationException("Absent group cannot have test");
  }

  @Override
  public void addBeforeBlock(Runnable aCodeBlock) {
    throw new UnsupportedOperationException("Absent group cannot have before block");
  }

  @Override
  public void addAfterBlock(Runnable aCodeBlock) {
    throw new UnsupportedOperationException("Absent group cannot have after block");
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

  public static AbsentGroup create() {
    AbsentGroup absentGroup = new AbsentGroup();
    absentGroup.testContext = NullContextDefinition.create();
    return absentGroup;
  }
}

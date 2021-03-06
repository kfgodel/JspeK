package info.kfgodel.jspek;

import info.kfgodel.jspek.ignored.DisabledSuiteSpecTest;
import info.kfgodel.jspek.ignored.OneTestInsideDisabledSpecTest;
import info.kfgodel.jspek.ignored.TwoPendingTestSpecTest;
import info.kfgodel.jspek.impl.model.SpecGroup;
import info.kfgodel.jspek.impl.model.SpecTest;
import info.kfgodel.jspek.impl.model.SpecTree;
import info.kfgodel.jspek.impl.model.TestContextDefinition;
import info.kfgodel.jspek.impl.parser.SpecParser;
import info.kfgodel.jspek.testSpecs.AfterUsedInOneTestSpecTest;
import info.kfgodel.jspek.testSpecs.BeforeAndAfterInheritedWhenNestedTest;
import info.kfgodel.jspek.testSpecs.BeforeUsedInOneTestSpecTest;
import info.kfgodel.jspek.testSpecs.EmptySpec;
import info.kfgodel.jspek.testSpecs.ItThrowsSpecTest;
import info.kfgodel.jspek.testSpecs.OneEmptyDescribeSpec;
import info.kfgodel.jspek.testSpecs.OneRootTestSpecTest;
import info.kfgodel.jspek.testSpecs.OneTestInsideDescribeSpecTest;
import info.kfgodel.jspek.testSpecs.TwoBeforeAndAfterTestSpecTest;
import info.kfgodel.jspek.testSpecs.TwoDescribeSpecsTest;
import info.kfgodel.jspek.testSpecs.VariableDefinedInParentContextSpecTest;
import info.kfgodel.jspek.testSpecs.VariableInSuitSpecTest;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class verifies that the spec parser read correctly the spec definitions from a JavaSpec subclass
 * Created by kfgodel on 12/07/14.
 */
public class SpecParserTest {

  private SpecParser parser;

  @Before
  public void createTheParser() {
    parser = SpecParser.create();
  }

  @Test
  public void itShouldParseAnEmptyTreeIfNoSpecDefined() {
    SpecTree readSpec = parser.parse(EmptySpec.class);
    assertThat(readSpec.hasNoTests()).isTrue();
  }

  @Test
  public void shouldContainOneEmptyGroup() {
    SpecTree readSpec = parser.parse(OneEmptyDescribeSpec.class);
    assertThat(readSpec.hasNoTests()).isTrue();

    SpecGroup rootGroup = readSpec.getRootGroup();
    List<SpecGroup> declaredGroups = rootGroup.getSubGroups();
    assertThat(declaredGroups).hasSize(1);

    SpecGroup onlyGroup = declaredGroups.get(0);
    assertThat(onlyGroup.isEmpty()).isTrue();
    assertThat(onlyGroup.getName()).isEqualTo("empty describe");
  }

  @Test
  public void theTreeShouldContainOneTestIfOneDefined() {
    SpecTree readSpec = parser.parse(OneRootTestSpecTest.class);
    assertThat(readSpec.hasNoTests()).isFalse();

    SpecGroup rootGroup = readSpec.getRootGroup();
    List<SpecTest> declaredTests = rootGroup.getDeclaredTests();

    assertThat(declaredTests).hasSize(1);

    SpecTest onlyTest = declaredTests.get(0);
    assertThat(onlyTest.getName()).isEqualTo("only test");
  }

  @Test
  public void shouldContainOneDescribeWithOneTest() {
    SpecTree readSpec = parser.parse(OneTestInsideDescribeSpecTest.class);

    SpecGroup rootGroup = readSpec.getRootGroup();
    SpecGroup onlyGroup = rootGroup.getSubGroups().get(0);
    assertThat(onlyGroup.getName()).isEqualTo("A suite");

    SpecTest onlyTest = onlyGroup.getDeclaredTests().get(0);
    assertThat(onlyTest.getName()).isEqualTo("contains spec with an expectation");
  }

  @Test
  public void shouldHaveTwoPendingTests() {
    SpecTree readSpec = parser.parse(TwoPendingTestSpecTest.class);

    List<SpecTest> declaredTest = readSpec.getRootGroup().getDeclaredTests();
    assertThat(declaredTest).hasSize(2);

    SpecTest firstTest = declaredTest.get(0);
    assertThat(firstTest.getName()).isEqualTo("xit pending test");
    assertThat(firstTest.isMarkedAsPending()).isTrue();
    assertThat(firstTest.getTestCode()).isNotNull();

    SpecTest secondTest = declaredTest.get(1);
    assertThat(secondTest.getName()).isEqualTo("non lambda pending test");
    assertThat(secondTest.isMarkedAsPending()).isTrue();
    assertThat(secondTest.getTestCode()).isNull();
  }

  @Test
  public void shouldHaveTwoDescribeContexts() {
    SpecTree readSpec = parser.parse(TwoDescribeSpecsTest.class);

    List<SpecGroup> declaredGroups = readSpec.getRootGroup().getSubGroups();

    SpecGroup firstGroup = declaredGroups.get(0);
    assertThat(firstGroup.getName()).isEqualTo("first group");
    assertThat(firstGroup.getDeclaredTests().get(0).getName()).isEqualTo("test in first group");

    SpecGroup secondGroup = declaredGroups.get(1);
    assertThat(secondGroup.getName()).isEqualTo("second group");
    assertThat(secondGroup.getDeclaredTests().get(0).getName()).isEqualTo("test in second group");
  }

  @Test
  public void shouldHaveADisabledSuite() {
    SpecTree readSpec = parser.parse(DisabledSuiteSpecTest.class);

    SpecGroup onlyGroup = readSpec.getRootGroup().getSubGroups().get(0);
    assertThat(onlyGroup.getName()).isEqualTo("a disabled spec");
    assertThat(onlyGroup.isMarkedAsDisabled()).isTrue();
  }

  @Test
  public void testShouldHaveABeforeCode() {
    SpecTree readSpec = parser.parse(BeforeUsedInOneTestSpecTest.class);

    SpecTest onlyTest = readSpec.getRootGroup().getDeclaredTests().get(0);
    assertThat(onlyTest.getName()).isEqualTo("test with before");

    List<Runnable> testBeforeBlocks = onlyTest.getBeforeBlocks();
    assertThat(testBeforeBlocks).hasSize(1);
  }

  @Test
  public void testShouldHaveAnAfterCode() {
    SpecTree readSpec = parser.parse(AfterUsedInOneTestSpecTest.class);

    SpecTest onlyTest = readSpec.getRootGroup().getDeclaredTests().get(0);
    assertThat(onlyTest.getName()).isEqualTo("test with after");

    List<Runnable> testAfterBlocks = onlyTest.getAfterBlocks();
    assertThat(testAfterBlocks).hasSize(1);
  }

  @Test
  public void testShouldHave2BeforeAnd2AfterCodes() {
    SpecTree readSpec = parser.parse(TwoBeforeAndAfterTestSpecTest.class);

    SpecTest onlyTest = readSpec.getRootGroup().getDeclaredTests().get(0);
    assertThat(onlyTest.getName()).isEqualTo("test with 2 before and 2 after");

    List<Runnable> testBeforeBlocks = onlyTest.getBeforeBlocks();
    assertThat(testBeforeBlocks).hasSize(2);

    List<Runnable> testAfterBlocks = onlyTest.getAfterBlocks();
    assertThat(testAfterBlocks).hasSize(2);
  }

  @Test
  public void shouldHave1RootTestWith1BeforeAfterAnd1NestedTestWith2BeforeAnd2AfterCodes() {
    SpecTree readSpec = parser.parse(BeforeAndAfterInheritedWhenNestedTest.class);

    SpecTest onlyRootTest = readSpec.getRootGroup().getDeclaredTests().get(0);
    assertThat(onlyRootTest.getName()).isEqualTo("test with 1 before/after set");

    List<Runnable> rootBeforeBlocks = onlyRootTest.getBeforeBlocks();
    assertThat(rootBeforeBlocks).hasSize(1);

    List<Runnable> rootAfterBlocks = onlyRootTest.getAfterBlocks();
    assertThat(rootAfterBlocks).hasSize(1);

    SpecGroup onlyGroup = readSpec.getRootGroup().getSubGroups().get(0);
    SpecTest nestedTest = onlyGroup.getDeclaredTests().get(0);

    List<Runnable> nestedTestBeforeBlocks = nestedTest.getBeforeBlocks();
    assertThat(nestedTestBeforeBlocks).hasSize(2);
    assertThat(nestedTestBeforeBlocks.get(0)).isEqualTo(rootBeforeBlocks.get(0));

    List<Runnable> nestedTestAfterBlocks = nestedTest.getAfterBlocks();
    assertThat(nestedTestAfterBlocks).hasSize(2);
    assertThat(nestedTestAfterBlocks.get(1)).isEqualTo(rootAfterBlocks.get(0));
  }


  @Test
  public void testInsideDisabledSpecShouldBePending() {
    SpecTree readSpec = parser.parse(OneTestInsideDisabledSpecTest.class);

    SpecGroup onlyGroup = readSpec.getRootGroup().getSubGroups().get(0);
    SpecTest disabledTest = onlyGroup.getDeclaredTests().get(0);

    assertThat(disabledTest.isMarkedAsPending()).isTrue();
  }

  @Test
  public void variableDefinedInGroupShouldHaveDefinition() {
    SpecTree readSpec = parser.parse(VariableInSuitSpecTest.class);

    SpecGroup onlyGroup = readSpec.getRootGroup().getSubGroups().get(0);

    TestContextDefinition contextDefinition = onlyGroup.getTestContext();
    assertThat(contextDefinition.getDefinitionFor("foo")).isNotNull();
  }

  @Test
  public void rootGroupShouldHaveVariableDefinitionAndChildGroupShouldUseParents() {
    SpecTree readSpec = parser.parse(VariableDefinedInParentContextSpecTest.class);

    SpecGroup rootGroup = readSpec.getRootGroup();
    Supplier<Object> rootDefinition = rootGroup.getTestContext().getDefinitionFor("foo").get();
    assertThat(rootDefinition).isNotNull();

    SpecGroup onlyGroup = rootGroup.getSubGroups().get(0);
    Supplier<Object> childDefinition = onlyGroup.getTestContext().getDefinitionFor("foo").get();
    assertThat(childDefinition).isSameAs(rootDefinition);
  }

  @Test
  public void specNameShouldIncludeExceptionAndConsequenceWhenItThrowsIsUsed() {
    SpecTree readSpec = parser.parse(ItThrowsSpecTest.class);
    assertThat(readSpec.hasNoTests()).isFalse();

    SpecGroup rootGroup = readSpec.getRootGroup();
    List<SpecTest> declaredTests = rootGroup.getDeclaredTests();

    assertThat(declaredTests).hasSize(1);

    SpecTest onlyTest = declaredTests.get(0);
    assertThat(onlyTest.getName()).isEqualTo("throws RuntimeException when a condition");
  }
}

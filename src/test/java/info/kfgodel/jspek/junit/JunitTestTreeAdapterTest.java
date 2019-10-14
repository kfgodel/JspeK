package info.kfgodel.jspek.junit;

import info.kfgodel.jspek.api.JavaSpec;
import info.kfgodel.jspek.impl.junit.JunitTestCode;
import info.kfgodel.jspek.impl.junit.JunitTestTreeAdapter;
import info.kfgodel.jspek.impl.model.SpecTree;
import info.kfgodel.jspek.impl.parser.SpecParser;
import info.kfgodel.jspek.testSpecs.EmptySpec;
import info.kfgodel.jspek.testSpecs.OneEmptyDescribeSpec;
import info.kfgodel.jspek.testSpecs.OneRootTestSpecTest;
import org.junit.Test;
import org.junit.runner.Description;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This type verifies the adapting of a spec tree to a junit tree
 * Created by kfgodel on 13/07/14.
 */
public class JunitTestTreeAdapterTest {

  @Test
  public void itNamesTheRootGroupAfterTheTestClass() {
    Class<? extends JavaSpec> specClass = EmptySpec.class;
    SpecTree specTree = SpecParser.create().parse(specClass);

    JunitTestTreeAdapter adapter = JunitTestTreeAdapter.create(specTree, specClass);
    assertThat(adapter.getJunitTree().getJunitDescription().getDisplayName()).isEqualTo(specClass.getName());
  }

  @Test
  public void itCreatesAnInnerGroupPerDescribe() {
    Class<? extends JavaSpec> specClass = OneEmptyDescribeSpec.class;
    SpecTree specTree = SpecParser.create().parse(specClass);

    JunitTestTreeAdapter adapter = JunitTestTreeAdapter.create(specTree, specClass);
    Description onlySubGroup = adapter.getJunitTree().getJunitDescription().getChildren().get(0);
    assertThat(onlySubGroup.getDisplayName()).isEqualTo("empty describe");

  }

  @Test
  public void itCreatesATestPerSpecTest() {
    Class<? extends JavaSpec> specClass = OneRootTestSpecTest.class;
    SpecTree specTree = SpecParser.create().parse(specClass);

    JunitTestTreeAdapter adapter = JunitTestTreeAdapter.create(specTree, specClass);
    List<JunitTestCode> junitTests = adapter.getJunitTree().getJunitTests().collect(Collectors.toList());
    assertThat(junitTests).hasSize(1);
    assertThat(junitTests.get(0).getTestDescription().getDisplayName()).isEqualTo("only test");

  }
}

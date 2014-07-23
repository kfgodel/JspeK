package ar.com.dgarcia.javaspec.junit;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.impl.junit.JunitTestCode;
import ar.com.dgarcia.javaspec.impl.junit.JunitTestTreeAdapter;
import ar.com.dgarcia.javaspec.impl.model.SpecTree;
import ar.com.dgarcia.javaspec.impl.parser.SpecParser;
import ar.com.dgarcia.javaspec.testSpecs.EmptySpec;
import ar.com.dgarcia.javaspec.testSpecs.OneEmptyDescribeSpec;
import ar.com.dgarcia.javaspec.testSpecs.OneRootTestSpec;
import org.junit.Test;
import org.junit.runner.Description;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This type verifies the adapting of a spec tree to a junit tree
 * Created by kfgodel on 13/07/14.
 */
public class JunitTestTreeAdapterTest {

    @Test
    public void itNamesTheRootGroupAfterTheTestClass(){
        Class<? extends JavaSpec> specClass = EmptySpec.class;
        SpecTree specTree = SpecParser.create().parse(specClass);

        JunitTestTreeAdapter adapter = JunitTestTreeAdapter.create(specTree, specClass);
        assertThat(adapter.getJunitTree().getJunitDescription().getDisplayName()).isEqualTo(specClass.getName());
    }

    @Test
    public void itCreatesAnInnerGroupPerDescribe(){
        Class<? extends JavaSpec> specClass = OneEmptyDescribeSpec.class;
        SpecTree specTree = SpecParser.create().parse(specClass);

        JunitTestTreeAdapter adapter = JunitTestTreeAdapter.create(specTree, specClass);
        Description onlySubGroup = adapter.getJunitTree().getJunitDescription().getChildren().get(0);
        assertThat(onlySubGroup.getDisplayName()).isEqualTo("empty describe");

    }

    @Test
    public void itCreatesATestPerSpecTest(){
        Class<? extends JavaSpec> specClass = OneRootTestSpec.class;
        SpecTree specTree = SpecParser.create().parse(specClass);

        JunitTestTreeAdapter adapter = JunitTestTreeAdapter.create(specTree, specClass);
        List<JunitTestCode> junitTests = adapter.getJunitTree().getJunitTests();
        assertThat(junitTests).hasSize(1);
        assertThat(junitTests.get(0).getTestDescription().getDisplayName()).isEqualTo("only test");

    }
}

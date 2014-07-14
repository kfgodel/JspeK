package ar.com.dgarcia.javaspec.junit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.junit.runner.Description;

import ar.com.dgarcia.javaspec.impl.junit.JunitTestCode;
import ar.com.dgarcia.javaspec.impl.junit.JunitTestTree;

/**
 * This type verifies that the JunitTestTree behaves as expected
 * Created by kfgodel on 13/07/14.
 */
public class JunitTestTreeTest {

    @Test
    public void itShouldHaveADescriptionNameWhenCreated(){
        JunitTestTree createdTree = JunitTestTree.create("a tree name");
        Description junitDescription = createdTree.getJunitDescription();
        assertThat(junitDescription.getDisplayName()).isEqualTo("a tree name");
    }

    @Test
    public void itShouldHaveNoTestWhenCreated(){
        JunitTestTree createdTree = JunitTestTree.create("a tree name");
        assertThat(createdTree.getJunitTests()).isEmpty();
    }

    @Test
    public void itShouldAddAGivenTest(){
        JunitTestCode givenTest = mock(JunitTestCode.class);
        JunitTestTree createdTree = JunitTestTree.create("a tree name");
        createdTree.addTest(givenTest);
        assertThat(createdTree.getJunitTests()).contains(givenTest);
    }
}

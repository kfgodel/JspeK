package info.kfgodel.jspek.junit;

import info.kfgodel.jspek.impl.junit.JunitTestCode;
import info.kfgodel.jspek.impl.junit.JunitTestTree;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Description;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * This type verifies that the JunitTestTree behaves as expected
 * Created by kfgodel on 13/07/14.
 */
public class JunitTestTreeTest {

	private JunitTestTree createdTree;

	@Before
	public void createTree(){
		Description rootDescription = Description.createSuiteDescription(JunitTestTreeTest.class);
		createdTree = JunitTestTree.create(rootDescription);
	}
	
    @Test
    public void itShouldHaveADescriptionNameWhenCreated(){
        Description junitDescription = createdTree.getJunitDescription();
        assertThat(junitDescription.getDisplayName()).isEqualTo(JunitTestTreeTest.class.getName());
    }

    @Test
    public void itShouldHaveNoTestWhenCreated(){
        assertThat(createdTree.getJunitTests()).isEmpty();
    }

    @Test
    public void itShouldAddAGivenTest(){
        JunitTestCode givenTest = mock(JunitTestCode.class);
        createdTree.addTest(givenTest);
        assertThat(createdTree.getJunitTests()).contains(givenTest);
    }
}

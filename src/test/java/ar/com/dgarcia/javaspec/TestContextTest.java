package ar.com.dgarcia.javaspec;

import ar.com.dgarcia.javaspec.api.TestContext;
import ar.com.dgarcia.javaspec.impl.context.MappedTestContext;
import ar.com.dgarcia.javaspec.impl.exceptions.SpecException;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

/**
 * This type verifies the behavior of the implementation for test context
 * Created by kfgodel on 20/07/14.
 */
public class TestContextTest  {


    private TestContext testContext;

    @Before
    public void createContext(){
        testContext = MappedTestContext.create();
    }


    @Test
    public void itCanDefineTheValueOfANamedVariable(){
        testContext.let("foo", ()-> 1);

        assertThat(testContext.<Integer>get("foo")).isEqualTo(1);
    }

    @Test
    public void itMemorizesTheValueOnceDefined(){
        Random random = new Random();
        testContext.let("rnd", () -> random.nextInt());

        Integer firstValue = testContext.<Integer>get("rnd");
        Integer secondValue = testContext.<Integer>get("rnd");
        Integer thirdValue = testContext.<Integer>get("rnd");

        assertThat(firstValue).isEqualTo(secondValue).isEqualTo(thirdValue);
    }

    @Test
    public void itThrowsExceptionIfTriedToChangeCurrentDefinition(){
        testContext.let("foo", () -> 1);

        try{
            testContext.let("foo", () -> 2);
            failBecauseExceptionWasNotThrown(SpecException.class);
        }catch(SpecException e){
            assertThat(e).hasMessage("Variable [foo] cannot be re-defined. Current definition: [1]");
        }
    }

    @Test
    public void itThrowsExceptionIfTriedToAccessUndefinedVariable(){
        try{
            testContext.get("undefined");
            failBecauseExceptionWasNotThrown(SpecException.class);
        }catch(SpecException e){
            assertThat(e).hasMessage("Variable [undefined] cannot be accessed because lacks definition");
        }
    }

    @Test
    public void itThrowsAnExceptionIfVariableDefinitionFails(){
        testContext.let("explosion", ()-> { throw new RuntimeException("Boom!"); } );

        try{
            testContext.get("explosion");
            failBecauseExceptionWasNotThrown(SpecException.class);
        }catch(SpecException e){
            assertThat(e).hasMessage("Definition for variable [explosion] failed to execute: Boom!");
        }

    }
}

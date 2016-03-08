package ar.com.dgarcia.javaspec;

import ar.com.dgarcia.javaspec.api.exceptions.SpecException;
import ar.com.dgarcia.javaspec.impl.context.MappedContext;
import ar.com.dgarcia.javaspec.impl.model.TestContextDefinition;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

/**
 * This type verifies the behavior of the implementation for test context
 * Created by kfgodel on 20/07/14.
 */
public class TestContextDefinitionTest {


    private TestContextDefinition testContext;

    @Before
    public void createContext(){
        testContext = MappedContext.create();
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
    public void itThrowsExceptionIfTriedToAccessUndefinedVariable(){
        try{
            testContext.get("undefined");
            failBecauseExceptionWasNotThrown(SpecException.class);
        }catch(SpecException e){
            assertThat(e).hasMessage("Variable [undefined] cannot be accessed because it lacks a definition in this context[{}]");
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

    @Test
    public void itUsesTheDefinitionOfParentContext(){
        TestContextDefinition parentContext = MappedContext.create();
        testContext.setParentDefinition(parentContext);

        parentContext.let("foo", ()-> 2);

        assertThat(testContext.<Integer>get("foo")).isEqualTo(2);
    }


    @Test
    public void itDetectsCyclicDependencies(){
        testContext.let("foo", ()-> testContext.get("bar"));
        testContext.let("bar", ()-> testContext.get("foo"));

        try{
            testContext.<Integer>get("foo");
            failBecauseExceptionWasNotThrown(SpecException.class);
        }catch( SpecException e){
            assertThat(e.getMessage()).startsWith("Got a Stackoverflow when evaluating variable [");
        }

    }
}

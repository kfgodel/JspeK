package info.kfgodel.jspek;

import info.kfgodel.jspek.api.exceptions.SpecException;
import info.kfgodel.jspek.impl.context.MappedContext;
import info.kfgodel.jspek.impl.model.TestContextDefinition;
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
  public void createContext() {
    testContext = MappedContext.create();
  }


  @Test
  public void itCanDefineTheValueOfANamedVariable() {
    testContext.let("foo", () -> 1);

    assertThat(testContext.<Integer>get("foo")).isEqualTo(1);
  }

  @Test
  public void itMemorizesTheValueOnceDefined() {
    Random random = new Random();
    testContext.let("rnd", () -> random.nextInt());

    Integer firstValue = testContext.<Integer>get("rnd");
    Integer secondValue = testContext.<Integer>get("rnd");
    Integer thirdValue = testContext.<Integer>get("rnd");

    assertThat(firstValue).isEqualTo(secondValue).isEqualTo(thirdValue);
  }

  @Test
  public void itThrowsExceptionIfTriedToAccessUndefinedVariable() {
    try {
      testContext.get("undefined");
      failBecauseExceptionWasNotThrown(SpecException.class);
    } catch (SpecException e) {
      assertThat(e).hasMessage("Variable [undefined] must be defined before accessing it in current context[{}]");
    }
  }

  @Test
  public void itForwardsTheOriginalExceptionIfVariableDefinitionFails() {
    testContext.let("explosion", () -> {
      throw new RuntimeException("Boom!");
    });

    try {
      testContext.get("explosion");
      failBecauseExceptionWasNotThrown(RuntimeException.class);
    } catch (RuntimeException e) {
      assertThat(e).hasMessage("Boom!");
    }
  }

  @Test
  public void itUsesTheDefinitionOfParentContext() {
    TestContextDefinition parentContext = MappedContext.create();
    testContext.setParentDefinition(parentContext);

    parentContext.let("foo", () -> 2);

    assertThat(testContext.<Integer>get("foo")).isEqualTo(2);
  }


  @Test
  public void itDetectsCyclicDependencies() {
    testContext.let("foo", () -> testContext.get("bar"));
    testContext.let("bar", () -> testContext.get("foo"));

    try {
      testContext.<Integer>get("foo");
      failBecauseExceptionWasNotThrown(SpecException.class);
    } catch (SpecException e) {
      assertThat(e.getMessage()).startsWith("Got a Stackoverflow when evaluating variable [");
    }

  }
}

package info.kfgodel.jspek.testSpecs;

import info.kfgodel.jspek.api.JavaSpec;
import info.kfgodel.jspek.api.JavaSpecRunner;
import info.kfgodel.jspek.api.contexts.TestContext;
import info.kfgodel.jspek.api.variable.Variable;
import info.kfgodel.jspek.impl.model.impl.GroupSpecDefinition;
import info.kfgodel.jspek.impl.model.impl.TestSpecDefinition;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 14/10/19 - 17:04
 */
@RunWith(JavaSpecRunner.class)
public class GroupAndTestNamingTest extends JavaSpec<TestContext> {
  @Override
  public void define() {
    describe("a group definition", () -> {
      itThrows(IllegalArgumentException.class, "when its name is null",()->{
        GroupSpecDefinition.create(null);
      }, e->{
        assertThat(e).hasMessage("Empty string cannot be used with it() or describe() because Junit doesn't support it");
      });
      itThrows(IllegalArgumentException.class, "when its name is empty",()->{
        GroupSpecDefinition.create(" ");
      }, e->{
        assertThat(e).hasMessage("Empty string cannot be used with it() or describe() because Junit doesn't support it");
      });
    });
    describe("a test definition", () -> {
      itThrows(IllegalArgumentException.class, "when its name is null",()->{
        TestSpecDefinition.create(null, Mockito.mock(Runnable.class), Variable.create());
      }, e->{
        assertThat(e).hasMessage("Empty string cannot be used with it() or describe() because Junit doesn't support it");
      });
      itThrows(IllegalArgumentException.class, "when its name is empty",()->{
        TestSpecDefinition.create(" ", Mockito.mock(Runnable.class), Variable.create());
      }, e->{
        assertThat(e).hasMessage("Empty string cannot be used with it() or describe() because Junit doesn't support it");
      });
    });

  }
}
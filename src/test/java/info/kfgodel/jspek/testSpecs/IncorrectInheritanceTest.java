package info.kfgodel.jspek.testSpecs;

import info.kfgodel.jspek.api.JavaSpec;
import info.kfgodel.jspek.api.JavaSpecRunner;
import info.kfgodel.jspek.api.contexts.TestContext;
import info.kfgodel.jspek.api.exceptions.SpecException;
import info.kfgodel.jspek.testSpecs.testobjects.DirectlyInheritedJavaspec;
import info.kfgodel.jspek.testSpecs.testobjects.IndirectlyInheritedJavaSpec;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 14/10/19 - 16:53
 */
@RunWith(JavaSpecRunner.class)
public class IncorrectInheritanceTest extends JavaSpec<TestContext> {
  @Override
  public void define() {
    describe("when implementing a java spec", () -> {

      itThrows(SpecException.class, "if the spec class is indirectly iherited from javaspec", () -> {
        new IndirectlyInheritedJavaSpec().populate(null);
      }, e -> {
        assertThat(e).hasMessage("A java spec class[class info.kfgodel.jspek.testSpecs.testobjects." +
          "IndirectlyInheritedJavaSpec] must inherit directly from class info.kfgodel.jspek.api.JavaSpec");
      });

      itThrows(SpecException.class, "if the spec class does not indicate type arguments", () -> {
        new DirectlyInheritedJavaspec().populate(null);
      }, e -> {
        assertThat(e).hasMessage("JavaSpec superclass must be generified with a type of TestContext. " +
          "For example JavaSpec<TestContext>");
      });

    });

  }
}
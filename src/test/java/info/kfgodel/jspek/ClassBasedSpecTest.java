package info.kfgodel.jspek;

import info.kfgodel.jspek.api.JavaSpec;
import info.kfgodel.jspek.api.JavaSpecRunner;
import info.kfgodel.jspek.api.contexts.ClassBasedTestContext;
import info.kfgodel.jspek.api.exceptions.SpecException;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

/**
 * This type verifies the correct behavior of class based specs
 * Created by kfgodel on 08/03/16.
 */
@RunWith(JavaSpecRunner.class)
public class ClassBasedSpecTest extends JavaSpec<ClassBasedTestContext<Object>> {
  @Override
  public void define() {

    describe("a class based spec", () -> {

      it("throws an error when the class is not defined but accessed", () -> {
        try {
          context().describedClass();
          failBecauseExceptionWasNotThrown(SpecException.class);
        } catch (SpecException e) {
          assertThat(e).hasMessage("Described class is not defined in this context[{}].\n" +
            "Use describe(class,lambda) to define it before accessing it");
        }
      });

      it("throws an error when the class is not defined but the subject is accessed", () -> {
        try {
          context().subject();
          failBecauseExceptionWasNotThrown(SpecException.class);
        } catch (SpecException e) {
          assertThat(e).hasMessage("Subject is not defined in this context[{}].\n" +
            "Use describe(class,lambda) to define a class whose subject is going to be tested");
        }
      });

      it("can define a class explicitly", () -> {
        context().describedClass(() -> Object.class);
        assertThat(context().describedClass()).isEqualTo(Object.class);
      });

      it("can define a subject explicitly without defining a class", () -> {
        context().subject(() -> "hello");
        assertThat(context().subject()).isEqualTo("hello");
      });

      describe("when the class is defined with #describe", () -> {
        describe(Object.class, () -> {
          it("returns the class when accessed", () -> {
            assertThat(context().describedClass()).isEqualTo(Object.class);
          });

          it("returns a test instance when subject is accessed", () -> {
            assertThat(context().subject()).isNotNull();
          });
        });
      });

      xdescribe(Object.class, () -> {
        it("can ignore groups of tests", () -> {
          // Never executed
          assertThat(true).isFalse();
        });
      });
    });
  }
}
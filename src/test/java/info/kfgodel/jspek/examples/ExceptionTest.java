package info.kfgodel.jspek.examples;

import info.kfgodel.jspek.api.JavaSpec;
import info.kfgodel.jspek.api.JavaSpecRunner;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 08/09/18 - 23:39
 */
@RunWith(JavaSpecRunner.class)
public class ExceptionTest extends JavaSpec<ExampleTestContext> {
  @Override
  public void define() {
    describe("a block of code", () -> {

      itThrows(RuntimeException.class, "when executed and can be catch with itThrows", () -> {
        throw new RuntimeException("Boom");
      }, e -> {
        assertThat(e).hasMessage("Boom");
      });
    });

    describe("a failing variable", () -> {
      context().collection(() -> {
        throw new RuntimeException("Failing to initialize");
      });

      itThrows(RuntimeException.class, "a custom exception that can be catch with itTrows", () -> {
        context().collection();
      }, e -> {
        assertThat(e).hasMessage("Failing to initialize");
      });

    });


  }
}
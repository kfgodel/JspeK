package info.kfgodel.jspek.examples;

import info.kfgodel.jspek.api.JavaSpec;
import info.kfgodel.jspek.api.JavaSpecRunner;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 08/09/18 - 22:27
 */
@RunWith(JavaSpecRunner.class)
public class DefiningScenariosTest extends JavaSpec<ExampleTestContext> {
  @Override
  public void define() {
    describe("when the word is Hello", () -> {
      context().word(() -> "Hello");

      it("is shorter than 6 characters", () -> {
        assertThat(context().word().length()).isLessThan(6);
      });
    });

    describe("when the word is goodbye", () -> {
      context().word(() -> "Goodbye");

      it("is longer than 6 characters", () -> {
        assertThat(context().word().length()).isGreaterThan(6);
      });
    });


  }
}
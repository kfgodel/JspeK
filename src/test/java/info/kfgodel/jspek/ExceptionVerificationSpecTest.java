package info.kfgodel.jspek;

import info.kfgodel.jspek.api.JavaSpec;
import info.kfgodel.jspek.api.JavaSpecRunner;
import info.kfgodel.jspek.api.contexts.TestContext;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This type verifies the behavior of a failure spec
 * Created by kfgodel on 28/04/16.
 */
@RunWith(JavaSpecRunner.class)
public class ExceptionVerificationSpecTest extends JavaSpec<TestContext> {
  @Override
  public void define() {
    describe("a spec that needs to check thrown exceptions", () -> {

      describe("asserting with an extra lambda", () -> {

        itThrows(RuntimeException.class, "always", () -> {
          throw new RuntimeException("Volare");
        }, (e) -> {
          assertThat(e).hasMessage("Volare");
        });

      });


    });

  }
}
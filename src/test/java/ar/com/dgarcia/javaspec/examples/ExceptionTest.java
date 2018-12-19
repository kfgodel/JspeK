package ar.com.dgarcia.javaspec.examples;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
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
      context().code(() ->
        () -> {throw new RuntimeException("Boom");}
      );

      itThrows(RuntimeException.class, "when executed", () -> {
        context().code().run();
      }, e -> {
        assertThat(e).hasMessage("Boom");
      });
    });


  }
}
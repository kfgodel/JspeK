package ar.com.dgarcia.javaspec.examples;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 09/09/18 - 17:17
 */
@RunWith(JavaSpecRunner.class)
public class UsingContextAliasTest extends JavaSpec<ExampleTestContext> {
  @Override
  public void define() {
    describe("a list", () -> {
      test().list(ArrayList::new);

      describe("when created", () -> {
        it("is empty", () -> {
          assertThat(test().list().isEmpty()).isTrue();
        });

        itThrows(IndexOutOfBoundsException.class, "if accessed to its first element", () -> {
          test().list().get(0);
        }, e -> {
          assertThat(e).hasMessage("Index: 0, Size: 0");
        });
      });

      describe("when an element is added", () -> {
        beforeEach(() -> {
          test().list().add("1");
        });
        it("returns the first list element when accessed", () -> {
          assertThat(test().list().get(0)).isEqualTo("1");
        });
        it("is not empty",()->{
            assertThat(test().list().isEmpty()).isFalse();
        });
      });


    });

  }
}
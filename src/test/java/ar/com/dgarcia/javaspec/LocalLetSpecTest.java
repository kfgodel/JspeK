package ar.com.dgarcia.javaspec;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.dgarcia.javaspec.api.contexts.TestContext;
import ar.com.dgarcia.javaspec.api.exceptions.SpecException;
import ar.com.dgarcia.javaspec.api.variable.Let;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This type tests usage of local lets
 * Created by nrainhart on 15/03/19.
 */
@RunWith(JavaSpecRunner.class)
public class LocalLetSpecTest extends JavaSpec<TestContext> {

  @Override
  public void define() {
    describe("when declaring a local let", () -> {

      describe("in a describe block", () -> {
        Let<Integer> foo = localLet("foo");

        describe("and setting its value", () -> {
          foo.set(() -> 1);
          it("can obtain that value",()->{
            assertThat(foo.get()).isEqualTo(1);
          });
        });
      });

      itThrows(SpecException.class, "when declared inside a running test",
        () -> { Let<Integer> foo = localLet("foo"); },
        e -> assertThat(e).hasMessage("A running test cannot declare a let object")
      );

    });

  }
}

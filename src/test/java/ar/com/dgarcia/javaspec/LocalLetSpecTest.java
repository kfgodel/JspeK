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
    describe("local lets", () -> {

      describe("can be declared in suite contexts", () -> {
        Let<Integer> foo = localLet("foo");

        describe("and its value can be set in contexts", () -> {
          foo.set(() -> 1);

          it("can obtain that value", () -> {
            assertThat(foo.get()).isEqualTo(1);
          });

          describe("when redefining its value in a sub-context", () -> {
            foo.set(() -> 2);

            it("changes the original value", () -> {
              assertThat(foo.get()).isEqualTo(2);
            });
          });

        });

        it("and its value can also be set inside a test", ()->{
          foo.set(() -> 3);

          assertThat(foo.get()).isEqualTo(3);
        });
      });

      itThrows(SpecException.class, "cannot be declared inside a running test",
          () -> { Let<Integer> foo = localLet("foo"); },
          e -> assertThat(e).hasMessage("A running test cannot declare a let object")
      );

    });

  }
}

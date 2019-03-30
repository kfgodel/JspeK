package ar.com.dgarcia.javaspec;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.dgarcia.javaspec.api.contexts.TestContext;
import ar.com.dgarcia.javaspec.api.exceptions.SpecException;
import ar.com.dgarcia.javaspec.api.variable.Let;
import org.junit.runner.RunWith;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

      describe("definitions are prioritized", ()->{

        Let<Integer> foo = localLet("foo");
        foo.set(()-> 1);

        beforeEach(()->{
          //This will override context definition
          foo.set(()-> 2);
        });

        it("setup definition will have precedence over suite definition", ()->{
          assertThat(foo.get()).isEqualTo(2);
        });

        it("it definition has precedence over the other two", ()->{
          foo.set(()-> 3);

          assertThat(foo.get()).isEqualTo(3);
        });
      });

      describe("one definition can use others", ()->{

        Let<Integer> sum = localLet("sum");
        Let<Integer> value = localLet("value");

        sum.set(()-> 2 + value.get());

        it("allowing to change parts of the test context", ()->{
          value.set(()-> 2);

          assertThat(sum.get()).isEqualTo(4);
        });

        describe("or nesting scenarios", ()->{
          value.set(()-> 1);

          it("with cleanly defined context", ()->{
            assertThat(sum.get()).isEqualTo(3);
          });
        });
      });

      describe("once defined", ()->{
        Let<Integer> random = localLet("random");
        random.set(()-> new Random().nextInt());

        it("the value remains the same through test duration", ()->{
          Integer firstTime = random.get();
          Integer secondTime = random.get();
          assertThat(secondTime).isSameAs(firstTime);
        });
      });

      describe("once its value is obtained in a test", () -> {
        Let<Integer> value = localLet("value");

        it("it cannot be redefined", ()-> {
          value.set(()-> 1);
          value.get();
          assertThatThrownBy(() -> value.set(()-> 2))
              .isInstanceOf(SpecException.class)
              .hasMessage("Variable [value] cannot be re-defined once assigned. Current value: [1]");
        });
      });
    });

  }
}

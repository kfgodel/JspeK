package info.kfgodel.jspek.examples;

import info.kfgodel.jspek.api.JavaSpec;
import info.kfgodel.jspek.api.JavaSpecRunner;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class is a simplified example to showcase some of the capabilities without going into full detail as
 * {@link info.kfgodel.jspek.ignored.JasmineLikeExampleTest}
 * <p>
 * Date: 15/10/19 - 21:58
 */
@RunWith(JavaSpecRunner.class)
public class ReadmeExampleJavaTest extends JavaSpec<ReadmeExampleTestContext> {
  @Override
  public void define() {
    describe("a java spec", () -> {

      it("contains a test with an expectation", () -> {
        assertThat(true).isEqualTo(true);
      });

      describe("when test variables are needed inside a context", () -> {
        test().age(() -> 23);

        it("can access the value defined in its declaration", () -> {
          assertThat(test().age()).isEqualTo(23);
        });

        it("can change its value inside a test", () -> {
          test().age(() -> 34);
          assertThat(test().age()).isEqualTo(34);
        });

        describe("when redefined inside a sub-context", () -> {
          test().age(() -> 21);

          it("accesses the redefined value", () -> {
            assertThat(test().age()).isEqualTo(21);
          });
        });

        describe("when the value cannot be defined during declaration", () -> {
          beforeEach(() -> {
            test().name(() -> "lazy name");
          });

          it("doesn't require special syntax for defining it later", () -> {
            assertThat(test().name()).isEqualTo("lazy name");
          });
        });

        describe("when dependent variables are needed", () -> {
          test().canBuyAlcohol(() -> test().age() >= 21);

          it("doesn't follow lexical scope", () -> { // I.e: dependent variable can be declared before independent
            assertThat(test().canBuyAlcohol()).isTrue();
          });

        });

      });
    });
  }
}
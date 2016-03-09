package ar.com.dgarcia.javaspec;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.dgarcia.javaspec.api.variable.Variable;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This type verifies the correct execution of test using given-when-then
 * where the assertion is the only part that varies between tests
 * <p/>
 * Created by kfgodel on 09/03/16.
 */
@RunWith(JavaSpecRunner.class)
public class VaryingAssertionCodeTest extends JavaSpec<GiveWhenThenTestContext> {
  @Override
  public void define() {
    describe("a given-when-then spec with context shared setup and exercise code", () -> {
      given(() -> {
        context().list(ArrayList::new);
      });
      when(() -> {
        context().list().add("element");
      });

      it("needs only to define the assertion code", () -> {
        Variable<Boolean> executed = Variable.of(false);
        then(() -> {
          executed.set(true);
          assertThat(context().list()).hasSize(1);
        });
        assertThat(executed.get()).isTrue();
      });
      it("defines a different test only by indicating a different assertion", () -> {
        then(() -> {
          assertThat(context().list()).containsExactly("element");
        });
      });
    });

  }
}
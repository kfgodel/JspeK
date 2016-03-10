package ar.com.dgarcia.javaspec;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This type verifies the behavior of a test with fixed setup and exercise code but
 * varying assertions per test
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

      it("only needs to define the assertion code to be run", () -> {
        then(() -> {
          assertThat(context().list()).hasSize(1);
        });
      });
      it("defines a different test only by indicating a different assertion", () -> {
        then(() -> {
          assertThat(context().list()).containsExactly("element");
        });
      });
    });

  }
}
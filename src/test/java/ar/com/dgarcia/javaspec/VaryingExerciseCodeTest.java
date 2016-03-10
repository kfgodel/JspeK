package ar.com.dgarcia.javaspec;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This type verifies the behavior of a test with fixed setup and assertion code,
 * and varying exercise code per test
 * Created by kfgodel on 09/03/16.
 */
@RunWith(JavaSpecRunner.class)
public class VaryingExerciseCodeTest extends JavaSpec<GiveWhenThenTestContext> {
  @Override
  public void define() {
    describe("a given-when-then spec with context shared setup and assertion code", () -> {
      given(() -> {
        context().list(ArrayList::new);
      });
      then(() -> {
        assertThat(context().list()).hasSize(1);
      });

      it("needs to define the exercise code and explicitly call for execution", () -> {
        when(() -> {
          context().list().add("hello");
        });
        executeAsGivenWhenThenTest();
      });
      it("defines a different test only by indicating a different exercise", () -> {
        when(() -> {
          context().list().add("bye");
        });
        executeAsGivenWhenThenTest();
      });

    });


  }
}
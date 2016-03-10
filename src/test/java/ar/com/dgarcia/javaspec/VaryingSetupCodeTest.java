package ar.com.dgarcia.javaspec;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This type verifies the behavior of a test with fixed exercise and assertion code,
 * and varying setup code per test
 * Created by kfgodel on 09/03/16.
 */
@RunWith(JavaSpecRunner.class)
public class VaryingSetupCodeTest extends JavaSpec<GiveWhenThenTestContext> {
  @Override
  public void define() {
    describe("a given-when-then spec with context shared setup and assertion code", () -> {
      when(() -> {
        context().list().add("element");
      });
      then(() -> {
        assertThat(context().list()).hasSize(1);
      });

      it("needs to define the setup code and explicitly call for execution", () -> {
        given(() -> {
          context().list(ArrayList::new);
        });
        executeAsGivenWhenThenTest();
      });
      it("defines a different test only by indicating a different setup", () -> {
        given(() -> {
          context().list(LinkedList::new);
        });
        executeAsGivenWhenThenTest();
      });

    });


  }
}
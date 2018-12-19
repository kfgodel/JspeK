package ar.com.dgarcia.javaspec.examples;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 09/09/18 - 16:13
 */
@RunWith(JavaSpecRunner.class)
public class GivenWhenThenTest extends JavaSpec<ExampleTestContext> {
  @Override
  public void define() {
    describe("Collection size", () -> {
      given(()->{
        context().collection().add("1");
      });
      when(()->{
        context().collection().add("1");
      });
      then(()->{
        assertThat(context().collection()).hasSize(2);
      });

      describe("when the collection is a list", () -> {
        context().collection(ArrayList::new);
        it("behaves as expected", this::executeAsGivenWhenThenTest);
      });

      describe("when the collection is a set", () -> {
        context().collection(HashSet::new);
        itThen("doesn't add duplicates", ()->{
          assertThat(context().collection()).hasSize(1);
        });
      });


    });

  }
}
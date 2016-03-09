package ar.com.dgarcia.javaspec;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This type verifies the use case of give-when-then structured tests
 * Created by kfgodel on 09/03/16.
 */
@RunWith(JavaSpecRunner.class)
public class GivenWhenThenSpecTest extends JavaSpec<GiveWhenThenTestContext> {
  @Override
  public void define() {
    describe("a given-when-then structured spec", () -> {
      it("has 3 parts communicated by the context", () -> {
        given(()->{
          context().list(ArrayList::new);
        });
        when(() -> {
          context().list().add("element");
        });
        then(()->{
          assertThat(context().list()).hasSize(1);
        });
      });
    });

  }

}
package ar.com.dgarcia.javaspec;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.dgarcia.javaspec.api.contexts.TestContext;
import ar.com.dgarcia.javaspec.api.exceptions.SpecException;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by kfgodel on 09/03/16.
 */
@RunWith(JavaSpecRunner.class)
public class GivenWhenThenUsedDuringDescriptionTest extends JavaSpec<TestContext> {
  @Override
  public void define() {
    describe("a given-when-then structured spec", () -> {
      // it cannot define setup code on the context
      try{
        given(()->{});
      }catch (SpecException e){
        assertThat(e).hasMessage("Setup code is not yet supported on contexts");
      }

      // it cannot define exercise code on the context
      try{
        when(()->{});
      }catch (SpecException e){
        assertThat(e).hasMessage("Excercise code is yet not supported on contexts");
      }

      // it cannot define assertion code on the context
      try{
        then(()->{});
      }catch (SpecException e){
        assertThat(e).hasMessage("Assertion code is yet not supported on contexts");
      }

      it("dummy test",()->{
          // Needed to avoid empty test validation
      });
    });

  }
}
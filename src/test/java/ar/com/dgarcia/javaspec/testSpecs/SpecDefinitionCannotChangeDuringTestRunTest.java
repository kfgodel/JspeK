package ar.com.dgarcia.javaspec.testSpecs;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.dgarcia.javaspec.api.TestContext;
import ar.com.dgarcia.javaspec.impl.exceptions.SpecException;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

/**
 * Created by tenpines on 31/12/15.
 */
@RunWith(JavaSpecRunner.class)
public class SpecDefinitionCannotChangeDuringTestRunTest extends JavaSpec<TestContext> {

  @Override
  public void define() {
    describe("during the execution of a test", ()->{

      it("another test cannot be defined", () -> {

        try{
          it("another test", ()->{});
          failBecauseExceptionWasNotThrown(SpecException.class);
        }catch (SpecException e){
          assertThat(e).hasMessage("Cannot call it(\"another test\") during the execution of a test");
        }

      });

      it("another group cannot be defined", () -> {
        try{
          describe("another group", ()->{});
          failBecauseExceptionWasNotThrown(SpecException.class);
        }catch (SpecException e){
          assertThat(e).hasMessage("Cannot call describe(\"another group\") during the execution of a test");
        }
      });
    });
  }
}

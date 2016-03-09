package ar.com.dgarcia.javaspec.testSpecs;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.dgarcia.javaspec.api.contexts.TestContext;
import ar.com.dgarcia.javaspec.api.exceptions.SpecException;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

/**
 * This type verifies the behavior of an incorrectly defined spec
 * Created by kfgodel on 08/03/16.
 */
@RunWith(JavaSpecRunner.class)
public class IncorrectContextForClassBasedSpec extends JavaSpec<TestContext> {
  @Override
  public void define() {
    describe("when a class based spec doesn't use the correct test context", () -> {
      it("throws an error when describe is called with a class",()->{
        try{
          describe(Object.class, ()->{});
          failBecauseExceptionWasNotThrown(SpecException.class);
        }catch(SpecException e){
          assertThat(e).hasMessage("#describe can't be called with a class if the test context is not a ClassBasedTestContext subtype");
        }
      });   
    });


  }
}
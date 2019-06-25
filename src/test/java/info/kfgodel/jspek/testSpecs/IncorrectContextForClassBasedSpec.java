package info.kfgodel.jspek.testSpecs;

import info.kfgodel.jspek.api.JavaSpec;
import info.kfgodel.jspek.api.JavaSpecRunner;
import info.kfgodel.jspek.api.contexts.TestContext;
import info.kfgodel.jspek.api.exceptions.SpecException;
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

      // it throws an error when describe is called with a class
      try{
        describe(Object.class, ()->{});
        failBecauseExceptionWasNotThrown(SpecException.class);
      }catch(SpecException e){
        assertThat(e).hasMessage("#describe can't be called with a class if the test context is not a ClassBasedTestContext subtype");
      }

      it("dummy test",()->{
          // needed to avoid empty test validation
      });   
    });


  }
}
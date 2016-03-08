package ar.com.dgarcia.javaspec.testSpecs;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.dgarcia.javaspec.api.TestContext;
import ar.com.dgarcia.javaspec.api.exceptions.SpecException;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

/**
 * This type verifies the correct behavior of class based specs
 * Created by kfgodel on 08/03/16.
 */
@RunWith(JavaSpecRunner.class)
public class ClassBasedSpec extends JavaSpec<TestContext> {
  @Override
  public void define() {

    describe("a class based spec", () -> {
      it("throws an error when the class is not defined yet",()->{
        try{
          context().describedClass();
          failBecauseExceptionWasNotThrown(SpecException.class);
        }catch(SpecException e){
          assertThat(e).hasMessage("Variable [describedClass] cannot be accessed because it lacks a definition in this context[{}]");
        }
      });


      describe("when the class is defined with #describe", () -> {
        describe(Object.class, ()->{
          it("returns the class instance when accessed",()->{
              assertThat(context().describedClass()).isEqualTo(Object.class);
          });   
        });
      });

    });
  }
}
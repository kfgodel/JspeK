package ar.com.dgarcia.javaspec;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.dgarcia.javaspec.api.contexts.TestContext;
import ar.com.dgarcia.javaspec.api.exceptions.SpecException;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

/**
 * This test verifies that given-then-when tests can manually modify the
 * context shared code
 * Created by kfgodel on 09/03/16.
 */
@RunWith(JavaSpecRunner.class)
public class ManualGWTContextManagementTest extends JavaSpec<TestContext> {
  @Override
  public void define() {
    describe("a given-when-then spec", () -> {

      describe("when no part is defined", () -> {
        it("throws an exception when the setup code is accessed", () -> {
          try {
            context().setupCode();
            failBecauseExceptionWasNotThrown(SpecException.class);
          } catch (SpecException e) {
            assertThat(e).hasMessage("Variable [setupCode] must be defined before accessing it in current context[{}]");
          }
        });
        it("throws an exception when the exercise code is accessed", () -> {
          try {
            context().exerciseCode();
            failBecauseExceptionWasNotThrown(SpecException.class);
          } catch (SpecException e) {
            assertThat(e).hasMessage("Variable [exerciseCode] must be defined before accessing it in current context[{}]");
          }
        });
        it("throws an exception when the assertion code is accessed", () -> {
          try {
            context().assertionCode();
            failBecauseExceptionWasNotThrown(SpecException.class);
          } catch (SpecException e) {
            assertThat(e).hasMessage("Variable [assertionCode] must be defined before accessing it in current context[{}]");
          }
        });
      });
    });

    it("can define the setup code in the context explicitly",()->{
      Runnable code = ()->{};
      context().setupCode(()-> code);
      assertThat(context().setupCode()).isSameAs(code);
    });
    it("can define the exercise code in the context explicitly",()->{
      Runnable code = ()->{};
      context().exerciseCode(()-> code);
      assertThat(context().exerciseCode()).isSameAs(code);
    });
    it("can define the assertion code in the context explicitly",()->{
      Runnable code = ()->{};
      context().assertionCode(()-> code);
      assertThat(context().assertionCode()).isSameAs(code);
    });

    describe("when parts are explicitly defined in the context", () -> {
      Runnable setup = ()->{};
      Runnable exercise = ()->{};
      Runnable assertion = ()->{};
      context().setupCode(()->setup);
      context().exerciseCode(()-> exercise);
      context().assertionCode(()-> assertion);

      it("can access the setup code in a test",()->{
          assertThat(context().setupCode()).isEqualTo(setup);
      });
      it("can access the exercise code in a test",()->{
        assertThat(context().exerciseCode()).isEqualTo(exercise);
      });
      it("can access the assertion code in a test",()->{
        assertThat(context().assertionCode()).isEqualTo(assertion);
      });
    });

    describe("when parts are implicitly defined in the context", () -> {
      Runnable setup = ()->{};
      Runnable exercise = ()->{};
      Runnable assertion = ()->{};
      given(setup);
      when(exercise);
      then(assertion);

      it("can access the setup code in a test",()->{
        assertThat(context().setupCode()).isEqualTo(setup);
      });
      it("can access the exercise code in a test",()->{
        assertThat(context().exerciseCode()).isEqualTo(exercise);
      });
      it("can access the assertion code in a test",()->{
        assertThat(context().assertionCode()).isEqualTo(assertion);
      });

    });


  }
}
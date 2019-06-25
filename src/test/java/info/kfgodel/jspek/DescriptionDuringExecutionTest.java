package info.kfgodel.jspek;

import info.kfgodel.jspek.api.JavaSpec;
import info.kfgodel.jspek.api.JavaSpecRunner;
import info.kfgodel.jspek.api.contexts.TestContext;
import info.kfgodel.jspek.api.exceptions.SpecException;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

/**
 * This type verifies the error messages generated when the specs are incorrectly
 * defined during test execution
 * Created by kfgodel on 09/03/16.
 */
@RunWith(JavaSpecRunner.class)
public class DescriptionDuringExecutionTest extends JavaSpec<TestContext> {
  @Override
  public void define() {
    describe("a spec during execution", () -> {

      it("throws an error if user tries to define another nested test", () -> {
        try {
          it("wrongly nested spec", () -> {});
          failBecauseExceptionWasNotThrown(SpecException.class);
        } catch (SpecException e) {
          assertThat(e).hasMessage("A running test cannot declare a nested spec calling it()");
        }
      });

      it("throws an error if user tries to define nested ignored tests", () -> {
        try {
          xit("wrongly nested ignored spec", () -> {});
          failBecauseExceptionWasNotThrown(SpecException.class);
        } catch (SpecException e) {
          assertThat(e).hasMessage("A running test cannot declare a nested ignored spec calling xit()");
        }
        try {
          xit("wrongly nested ignored spec");
          failBecauseExceptionWasNotThrown(SpecException.class);
        } catch (SpecException e) {
          assertThat(e).hasMessage("A running test cannot declare a nested ignored spec calling xit()");
        }
      });

      it("throws an error if user tries to describe a group spec",()->{
        try {
          describe("wrongly nested group", () -> {});
          failBecauseExceptionWasNotThrown(SpecException.class);
        } catch (SpecException e) {
          assertThat(e).hasMessage("A running test cannot declare a group spec calling describe()");
        }
      });

      it("throws an error if user tries to describe a class spec",()->{
        try {
          describe(Object.class, () -> {});
          failBecauseExceptionWasNotThrown(SpecException.class);
        } catch (SpecException e) {
          assertThat(e).hasMessage("A running test cannot declare a class spec calling describe()");
        }
      });

      it("throws an error if user tries to describe an ignored group spec",()->{
        try {
          xdescribe("wrongly nested class group", () -> {});
          failBecauseExceptionWasNotThrown(SpecException.class);
        } catch (SpecException e) {
          assertThat(e).hasMessage("A running test cannot declare an ignored group spec calling xdescribe()");
        }
      });

      it("throws an error if user tries to describe an ignored class spec",()->{
        try {
          xdescribe(Object.class, () -> {});
          failBecauseExceptionWasNotThrown(SpecException.class);
        } catch (SpecException e) {
          assertThat(e).hasMessage("A running test cannot declare an ignored class spec calling xdescribe()");
        }
      });

      it("throws an error if user tries to add before blocks",()->{
        try {
          beforeEach(() -> {});
          failBecauseExceptionWasNotThrown(SpecException.class);
        } catch (SpecException e) {
          assertThat(e).hasMessage("A running test cannot declare a before block calling beforeEach()");
        }
      });

      it("throws an error if user tries to add after blocks",()->{
        try {
          afterEach(() -> {});
          failBecauseExceptionWasNotThrown(SpecException.class);
        } catch (SpecException e) {
          assertThat(e).hasMessage("A running test cannot declare an after block calling afterEach()");
        }
      });

      it("throws an error if user tries to declare another nested failure test",()->{
        try {
          itThrows(RuntimeException.class, "", ()->{}, (e)->{});
          failBecauseExceptionWasNotThrown(SpecException.class);
        } catch (SpecException e) {
          assertThat(e).hasMessage("A running test cannot declare a nested failure test calling itThrows()");
        }
      });

      describe("when declaration is done on a before/after block", () -> {
        beforeEach(()->{
          testAllDeclarationAttempts();
        });
        it("it throws the expected exceptions",()->{
            // The actual assertion are on the before/after blocks
        });   
        afterEach(()->{
          testAllDeclarationAttempts();
        });
      });


    });

  }

  private void testAllDeclarationAttempts() {
    try {
      it("wrongly nested spec", () -> {});
      failBecauseExceptionWasNotThrown(SpecException.class);
    } catch (SpecException e) {
      assertThat(e).hasMessage("A running test cannot declare a nested spec calling it()");
    }
    try {
      xit("wrongly nested ignored spec", () -> {});
      failBecauseExceptionWasNotThrown(SpecException.class);
    } catch (SpecException e) {
      assertThat(e).hasMessage("A running test cannot declare a nested ignored spec calling xit()");
    }
    try {
      xit("wrongly nested ignored spec");
      failBecauseExceptionWasNotThrown(SpecException.class);
    } catch (SpecException e) {
      assertThat(e).hasMessage("A running test cannot declare a nested ignored spec calling xit()");
    }
    try {
      describe("wrongly nested group", () -> {});
      failBecauseExceptionWasNotThrown(SpecException.class);
    } catch (SpecException e) {
      assertThat(e).hasMessage("A running test cannot declare a group spec calling describe()");
    }
    try {
      describe(Object.class, () -> {});
      failBecauseExceptionWasNotThrown(SpecException.class);
    } catch (SpecException e) {
      assertThat(e).hasMessage("A running test cannot declare a class spec calling describe()");
    }
    try {
      xdescribe("wrongly nested class group", () -> {});
      failBecauseExceptionWasNotThrown(SpecException.class);
    } catch (SpecException e) {
      assertThat(e).hasMessage("A running test cannot declare an ignored group spec calling xdescribe()");
    }
    try {
      xdescribe(Object.class, () -> {});
      failBecauseExceptionWasNotThrown(SpecException.class);
    } catch (SpecException e) {
      assertThat(e).hasMessage("A running test cannot declare an ignored class spec calling xdescribe()");
    }
    try {
      beforeEach(() -> {});
      failBecauseExceptionWasNotThrown(SpecException.class);
    } catch (SpecException e) {
      assertThat(e).hasMessage("A running test cannot declare a before block calling beforeEach()");
    }
    try {
      afterEach(() -> {});
      failBecauseExceptionWasNotThrown(SpecException.class);
    } catch (SpecException e) {
      assertThat(e).hasMessage("A running test cannot declare an after block calling afterEach()");
    }

  }
}
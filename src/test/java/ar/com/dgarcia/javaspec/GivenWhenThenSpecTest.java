package ar.com.dgarcia.javaspec;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.dgarcia.javaspec.api.exceptions.SpecException;
import org.assertj.core.util.Lists;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

/**
 * This type verifies the use case of give-when-then structured tests
 * Created by kfgodel on 09/03/16.
 */
@RunWith(JavaSpecRunner.class)
public class GivenWhenThenSpecTest extends JavaSpec<GiveWhenThenTestContext> {
  @Override
  public void define() {
    describe("a given-when-then structured spec", () -> {

      it("has 3 parts executed in order when 'then' is defined", () -> {
        List<String> parts = new ArrayList<String>();
        given(() -> {
          parts.add("setup");
        });
        when(() -> {
          parts.add("exercise");
        });
        then(() -> {
          parts.add("assert");
        });
        assertThat(parts).isEqualTo(Lists.newArrayList("setup", "exercise", "assert"));
      });

      it("communicates each part using the context", () -> {
        given(() -> {
          context().list(ArrayList::new);
        });
        when(() -> {
          context().list().add("element");
        });
        then(() -> {
          assertThat(context().list()).hasSize(1);
        });
      });
      
      it("throws an error if the setup code is not defined",()->{
        try{
          then(() -> {
            assertThat(true).isTrue();
          });
          failBecauseExceptionWasNotThrown(SpecException.class);
        }catch (SpecException e){
          assertThat(e.getMessage()).startsWith("Variable [setupCode] cannot be accessed because it lacks a definition in this context");
        }
      });
      it("throws an error if the exercise code is not defined",()->{
        given(()->{
          context().list(ArrayList::new);
        });
        try{
          then(() -> {
            assertThat(true).isTrue();
          });
          failBecauseExceptionWasNotThrown(SpecException.class);
        }catch (SpecException e){
          assertThat(e.getMessage()).startsWith("Variable [exerciseCode] cannot be accessed because it lacks a definition in this context");
        }
      });
      it("throws an error if the assertion code is not defined", () -> {
        given(() -> {
          context().list(ArrayList::new);
        });
        when(() -> {
          context().list().add("element");
        });
        try{
          executeAsGivenWhenThenTest();
          failBecauseExceptionWasNotThrown(SpecException.class);
        }catch (SpecException e){
          assertThat(e.getMessage()).startsWith("Variable [assertionCode] cannot be accessed because it lacks a definition in this context");
        }
      });


      describe("when setup and exercise code are already defined on the context", () -> {
        given(() -> {
          context().list(ArrayList::new);
        });
        when(() -> {
          context().list().add("hello");
        });
        it("completes the test by defining the assertion code", () -> {
          then(() -> {
            assertThat(context().list()).containsExactly("hello");
          });
        });
        it("runs automatically when the assertion code is defined", () -> {
          then(() -> {
            assertThat(context().list()).isNotEmpty();
          });
        });
      });

      describe("when setup code is the missing part on the context", () -> {
        when(() -> {
          context().list().add("something");
        });
        then(() -> {
          assertThat(context().list()).isNotEmpty();
        });

        it("needs to execute explicitly after defining the missing part", () -> {
          given(() -> {
            context().list(ArrayList::new);
          });
          executeAsGivenWhenThenTest();
        });
        it("allows the redefinition of the exercise", () -> {
          given(() -> {
            context().list(ArrayList::new);
          });
          when(() -> {
            context().list().add("something else");
          });
          executeAsGivenWhenThenTest();
        });
        it("doesn't need explicit execution when redefining the assertion", () -> {
          given(() -> {
            context().list(LinkedList::new);
          });
          then(() -> {
            assertThat(context().list()).isInstanceOf(LinkedList.class);
          });
        });
      });

      describe("when exercise code is the missing part on the context", () -> {
        given(() -> {
          context().list(ArrayList::new);
        });
        then(() -> {
          assertThat(context().list()).isNotEmpty();
        });

        it("also needs to execute explicitly after defining the missing part", () -> {
          when(() -> {
            context().list().add("something");
          });
          executeAsGivenWhenThenTest();
        });
        it("allows redefinition of the setup code",()->{
          given(() -> {
            context().list(LinkedList::new);
          });
          when(() -> {
            context().list().add("something");
          });
          executeAsGivenWhenThenTest();
        });
        it("doesn't need explicit execution when redefining the assertion", () -> {
          when(() -> {
            context().list().add("something");
          });
          then(() -> {
            assertThat(context().list()).isInstanceOf(ArrayList.class);
          });
        });
      });


    });

  }

}
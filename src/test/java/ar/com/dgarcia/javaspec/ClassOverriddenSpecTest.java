package ar.com.dgarcia.javaspec;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.dgarcia.javaspec.api.contexts.ClassBasedTestContext;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This type verifies that subject instances change if described class changes
 * Created by kfgodel on 08/03/16.
 */
@RunWith(JavaSpecRunner.class)
public class ClassOverriddenSpecTest extends JavaSpec<ClassBasedTestContext<List>> {
  @Override
  public void define() {
    describe("a context with one described class", () -> {
      describe(ArrayList.class, ()->{

        it("has instances of that class as subject",()->{
            assertThat(context().subject()).isInstanceOf(ArrayList.class);
        });

        describe("when class is redefined in a sub-context", () -> {
          describe(LinkedList.class, ()->{
            it("has instances of the new class as subject",()->{
              assertThat(context().subject()).isInstanceOf(LinkedList.class);
            });
          });
        });

      });
    });

  }
}
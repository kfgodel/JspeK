package ar.com.dgarcia.javaspec;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.dgarcia.javaspec.api.contexts.ClassBasedTestContext;
import com.google.common.collect.Lists;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This type verifies that a custom subject that depend on the described class
 * receives different class if class is overriden
 * Created by kfgodel on 08/03/16.
 */
@RunWith(JavaSpecRunner.class)
public class CustomSubjectWithOverriddenClassSpecTest extends JavaSpec<ClassBasedTestContext<List>> {
  @Override
  public void define() {
    describe("a custom subject depending on the described class", () -> {
      // A list with the described class as only element
      context().subject(()-> Lists.newArrayList(context().describedClass()));

      describe("when a class is defined", () -> {
        describe(ArrayList.class, ()->{
          it("has the first class as element",()->{
            assertThat(context().subject()).isEqualTo(Lists.newArrayList(ArrayList.class));
          });
        });

        describe("when the class is redefined", () -> {
          describe(LinkedList.class, ()->{
            it("has the second class as element",()->{
              assertThat(context().subject()).isEqualTo(Lists.newArrayList(LinkedList.class));
            });   
          });
        });

      });


    });

  }
}
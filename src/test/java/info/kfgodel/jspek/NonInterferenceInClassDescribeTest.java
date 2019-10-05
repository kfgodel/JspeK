package info.kfgodel.jspek;

import info.kfgodel.jspek.api.JavaSpec;
import info.kfgodel.jspek.api.JavaSpecRunner;
import info.kfgodel.jspek.api.contexts.ClassBasedTestContext;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This type verifies that describing a class in a nested context doesn't interfere with parent context
 * Created by kfgodel on 13/03/16.
 */
@RunWith(JavaSpecRunner.class)
public class NonInterferenceInClassDescribeTest extends JavaSpec<ClassBasedTestContext<Object>> {

  @Override
  public void define() {
    describe("a class based group", () -> {

      context().describedClass(()-> String.class);

      describe(Object.class, () -> {
        it("has the class a context variable inside the group",()->{
            assertThat(context().describedClass()).isEqualTo(Object.class);
        });   
      });
      
      it("doesn't affect the parent context definition",()->{
        assertThat(context().describedClass()).isEqualTo(String.class);
      });

    });

  }
}
package ar.com.dgarcia.javaspec;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.dgarcia.javaspec.api.contexts.ClassBasedTestContext;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by kfgodel on 09/03/16.
 */
@RunWith(JavaSpecRunner.class)
public class IgnoredClassBasedSpecTest extends JavaSpec<ClassBasedTestContext<Object>> {
  @Override
  public void define() {
    describe("an ignored class based group", () -> {

      xdescribe(Object.class, ()->{
        it("is never executed",()->{
            assertThat("it actually ran").isEqualTo("This test never runs");
        });
      });

    });

  }
}
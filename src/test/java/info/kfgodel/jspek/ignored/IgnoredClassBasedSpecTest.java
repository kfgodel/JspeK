package info.kfgodel.jspek.ignored;

import info.kfgodel.jspek.api.JavaSpec;
import info.kfgodel.jspek.api.JavaSpecRunner;
import info.kfgodel.jspek.api.contexts.ClassBasedTestContext;
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

      xdescribe(Object.class, () -> {
        it("is never executed", () -> {
          assertThat("it actually ran").isEqualTo("This test never runs");
        });
      });

    });

  }
}
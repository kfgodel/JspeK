package ar.com.dgarcia.javaspec.testSpecs;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.dgarcia.javaspec.api.contexts.TestContext;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This test verifies that null can be defined as a valid value for a context variable
 * Date: 17/12/18 - 23:09
 */
@RunWith(JavaSpecRunner.class)
public class UseNullAsValidValueTest extends JavaSpec<TestContext> {
  @Override
  public void define() {
    describe("a null valued variable", () -> {
      context().let("aVariable", () -> null);

      it("can be used on tests", () -> {
        assertThat(context().<Object>get("aVariable")).isNull();
      });
    });

  }
}

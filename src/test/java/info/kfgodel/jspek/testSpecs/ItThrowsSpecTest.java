package info.kfgodel.jspek.testSpecs;

import info.kfgodel.jspek.api.JavaSpec;
import info.kfgodel.jspek.api.JavaSpecRunner;
import info.kfgodel.jspek.api.contexts.TestContext;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class serves as input spec for parser test
 * Created by kfgodel on 28/04/16.
 */
@RunWith(JavaSpecRunner.class)
public class ItThrowsSpecTest extends JavaSpec<TestContext> {
  @Override
  public void define() {
    itThrows(RuntimeException.class, "when a condition", ()->{
      throw new RuntimeException("Boom");
    }, (e)->{
      assertThat(e).hasMessage("Boom");
    });
  }
}
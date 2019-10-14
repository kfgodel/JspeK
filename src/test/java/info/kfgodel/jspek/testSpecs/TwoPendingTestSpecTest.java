package info.kfgodel.jspek.testSpecs;

import info.kfgodel.jspek.api.JavaSpec;
import info.kfgodel.jspek.api.JavaSpecRunner;
import info.kfgodel.jspek.api.contexts.TestContext;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class serves as input spec for parser test
 * Created by kfgodel on 12/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class TwoPendingTestSpecTest extends JavaSpec<TestContext> {
  @Override
  public void define() {
    xit("xit pending test", () -> {
      assertThat(true).isEqualTo(false);
    });

    xit("non lambda pending test");
  }
}

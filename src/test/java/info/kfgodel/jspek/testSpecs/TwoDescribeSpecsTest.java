package info.kfgodel.jspek.testSpecs;

import info.kfgodel.jspek.api.JavaSpec;
import info.kfgodel.jspek.api.JavaSpecRunner;
import info.kfgodel.jspek.api.contexts.TestContext;
import org.junit.runner.RunWith;

/**
 * This class serves as input spec for parser test
 * Created by kfgodel on 12/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class TwoDescribeSpecsTest extends JavaSpec<TestContext> {
  @Override
  public void define() {
    describe("first group", () -> {
      it("test in first group", () -> {

      });
    });

    describe("second group", () -> {
      it("test in second group", () -> {

      });
    });
  }
}

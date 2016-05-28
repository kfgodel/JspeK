package ar.com.dgarcia.javaspec;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.dgarcia.javaspec.api.contexts.TestContext;
import ar.com.dgarcia.javaspec.api.variable.Variable;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class verifies that a failed test doesn't stop an after block from being executed.
 *
 * This test cannot be run as part of the suits because it fails one of the tests
 *
 * Created by kfgodel on 28/05/16.
 */
@RunWith(JavaSpecRunner.class)
public class AfterBlockExecutedEvenIfTestFailsTest extends JavaSpec<TestContext> {
  @Override
  public void define() {
    Variable<Integer> counter = Variable.of(0);
    xdescribe("when an after block is defined", () -> {
      afterEach(() -> {
        counter.storeSumWith(1);
      });

      it("even if a test fails", () -> {
        throw new RuntimeException("failing");
      });

      it("its after block gets executed", () -> {
        assertThat(counter.get()).isEqualTo(1);
      });
    });

  }
}
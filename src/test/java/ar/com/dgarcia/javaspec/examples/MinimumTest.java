package ar.com.dgarcia.javaspec.examples;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.dgarcia.javaspec.api.contexts.TestContext;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class serves as an example of the minimum requirements for running a java-spec test
 * Date: 08/09/18 - 21:56
 */
@RunWith(JavaSpecRunner.class)
public class MinimumTest extends JavaSpec<TestContext> {
  @Override
  public void define() {

    xit("has a length of 12 characters", () -> {
      assertThat("Hello World!").hasSize(12);
    });

  }
}
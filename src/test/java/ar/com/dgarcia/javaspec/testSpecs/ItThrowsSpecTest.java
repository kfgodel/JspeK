package ar.com.dgarcia.javaspec.testSpecs;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.dgarcia.javaspec.api.contexts.TestContext;
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
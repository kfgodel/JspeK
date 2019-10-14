package info.kfgodel.jspek.junit;

import info.kfgodel.jspek.api.JavaSpecRunner;
import info.kfgodel.jspek.junit.testobjects.NoTestsSpec;
import org.junit.Test;
import org.junit.runners.model.InitializationError;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 14/10/19 - 18:11
 */
public class JavaSpecRunnerTest {

  @Test
  public void itShouldReceiveAJavaSpecClass() {
    try {
      new JavaSpecRunner(List.class, null);
    } catch (InitializationError e) {
      assertThat(e.getCauses().get(0)).hasMessage("Your class[interface java.util.List] must extend class " +
        "info.kfgodel.jspek.api.JavaSpec to be run with JavaSpecRunner");
    }
  }

  @Test
  public void itShouldReceiveAJavaSpecClassWithAtLeastATest() {
    try {
      new JavaSpecRunner(NoTestsSpec.class, null);
    } catch (InitializationError e) {
      assertThat(e.getCauses().get(0)).hasMessage("The spec class[NoTestsSpec] has no tests. You must at least " +
        "use one it() or one xit() inside your definition method");
    }
  }
}

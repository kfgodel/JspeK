package info.kfgodel.jspek.api;

import info.kfgodel.jspek.impl.junit.JunitTestCode;
import info.kfgodel.jspek.impl.junit.JunitTestTreeAdapter;
import info.kfgodel.jspek.impl.model.SpecTree;
import info.kfgodel.jspek.impl.parser.SpecParser;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

import java.util.List;

/**
 * This type implements the Java Spec runner needed to extend Junit running mechanism
 * Created by kfgodel on 12/07/14.
 */
public class JavaSpecRunner extends Runner {

  private Class<? extends JavaSpec> clase;
  private JunitTestTreeAdapter junitAdapter;

  /**
   * Called reflectively on classes annotated with <code>@RunWith(Suite.class)</code>
   *
   * @param klass   the root class
   * @param builder builds runners for classes in the suite
   */
  @SuppressWarnings("unchecked")
  public JavaSpecRunner(Class<?> klass, RunnerBuilder builder) throws InitializationError {
    if (!JavaSpec.class.isAssignableFrom(klass)) {
      throw new InitializationError("Your class[" + klass + "] must extend " + JavaSpec.class + " to be run with " + JavaSpecRunner.class.getSimpleName());
    }
    this.clase = (Class<? extends JavaSpec>) klass;
    createJunitTestTreeFromSpecClass();
  }

  /**
   * Creates the test tree to be used to describe and execute the tests in junit
   */
  private void createJunitTestTreeFromSpecClass() throws InitializationError {
    SpecTree specTree = SpecParser.create().parse(clase);
    if (specTree.hasNoTests()) {
      throw new InitializationError("The spec class[" + clase.getSimpleName() + "] has no tests. You must at least use one it() or one xit() inside your definition method");
    }
    junitAdapter = JunitTestTreeAdapter.create(specTree, clase);
  }


  @Override
  public Description getDescription() {
    return junitAdapter.getJunitTree().getJunitDescription();
  }

  @Override
  public void run(RunNotifier notifier) {
    List<JunitTestCode> adaptedTests = junitAdapter.getJunitTree().getJunitTests();
    for (JunitTestCode adaptedTest : adaptedTests) {
      adaptedTest.executeNotifying(notifier);
    }
  }
}

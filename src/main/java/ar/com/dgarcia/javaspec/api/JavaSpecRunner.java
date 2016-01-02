package ar.com.dgarcia.javaspec.api;

import ar.com.dgarcia.javaspec.impl.junit.JunitTestCode;
import ar.com.dgarcia.javaspec.impl.junit.JunitTestTreeAdapter;
import ar.com.dgarcia.javaspec.impl.model.SpecTree;
import ar.com.dgarcia.javaspec.impl.parser.SpecParser;
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

    private JunitTestTreeAdapter junitAdapter;

    /**
     * Called reflectively on classes annotated with <code>@RunWith(Suite.class)</code>
     *
     * @param klass the root class
     * @param builder builds runners for classes in the suite
     */
    @SuppressWarnings("unchecked")
	public JavaSpecRunner(Class<?> klass, RunnerBuilder builder) throws InitializationError {
        SpecParser specParser = SpecParser.create();
        SpecTree specTree = null;
        try {
            specTree = specParser.parse(klass);
        } catch (IllegalArgumentException e) {
            throw new InitializationError(e);
        }
        if(specTree.hasNoTests()){
            throw new InitializationError("The spec class["+ ((Class<? extends JavaSpec>) klass).getSimpleName()+"] has no tests. You must at least use one it() or one xit() inside your definition method");
        }
        junitAdapter = JunitTestTreeAdapter.create(specTree);
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

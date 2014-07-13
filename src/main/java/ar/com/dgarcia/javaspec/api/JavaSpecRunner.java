package ar.com.dgarcia.javaspec.api;

import ar.com.dgarcia.javaspec.impl.exceptions.SpecException;
import ar.com.dgarcia.javaspec.impl.junit.JunitTestCode;
import ar.com.dgarcia.javaspec.impl.junit.JunitTestTreeAdapter;
import ar.com.dgarcia.javaspec.impl.model.SpecTree;
import ar.com.dgarcia.javaspec.impl.parser.SpecParser;
import org.junit.internal.runners.model.EachTestNotifier;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

import javax.smartcardio.TerminalFactory;
import java.util.List;

/**
 * This type implements the Java Spec runner needed to extend Junit running mechanism
 * Created by kfgodel on 12/07/14.
 */
public class JavaSpecRunner extends Runner {

    private Class<? extends JavaSpec> clase;
    private Description testIgnored;
    private Description testOk;
    private Description testFailed;
    private JunitTestTreeAdapter junitAdapter;

    /**
     * Called reflectively on classes annotated with <code>@RunWith(Suite.class)</code>
     *
     * @param klass the root class
     * @param builder builds runners for classes in the suite
     */
    public JavaSpecRunner(Class<?> klass, RunnerBuilder builder) throws InitializationError {
        if(!JavaSpec.class.isAssignableFrom(klass)){
            throw new InitializationError("Your class["+klass+"] must extend " + JavaSpec.class + " to be run with " +  JavaSpecRunner.class.getSimpleName());
        }
        this.clase = (Class<? extends JavaSpec>) klass;
        createJunitTestTreeFromSpecClass();
    }

    /**
     * Creates the test tree to be used to describe and execute the tests in junit
     */
    private void createJunitTestTreeFromSpecClass() throws InitializationError {
        SpecTree specTree = SpecParser.create().parse(clase);
        if(specTree.hasNoTests()){
            throw new InitializationError("The spec class["+clase.getSimpleName()+"] has no tests. You must at least use one it() or one xit() inside your definition method");
        }
        junitAdapter = JunitTestTreeAdapter.create(specTree, clase);
    }


    @Override
    public Description getDescription() {
        return junitAdapter.getJunitTree().getJunitDescription();
//        Description descripcion = Description.createSuiteDescription("raiz");
//
//        Description grupo1 = Description.createSuiteDescription("grupo1");
//        testOk = Description.createSuiteDescription("testOk");
//        grupo1.addChild(testOk);
//        testFailed = Description.createSuiteDescription("testFailed");
//        grupo1.addChild(testFailed);
//
//        descripcion.addChild(grupo1);
//
//        Description grupo2 = Description.createSuiteDescription("grupo2");
//        testIgnored = Description.createSuiteDescription("testIgnored");
//        grupo2.addChild(testIgnored);
//        descripcion.addChild(grupo2);
//
//        return descripcion;
    }

    @Override
    public void run(RunNotifier notifier) {
        List<JunitTestCode> adaptedTests = junitAdapter.getJunitTree().getJunitTests();
        for (JunitTestCode adaptedTest : adaptedTests) {
            adaptedTest.executeNotifying(notifier);
        }
    }
}

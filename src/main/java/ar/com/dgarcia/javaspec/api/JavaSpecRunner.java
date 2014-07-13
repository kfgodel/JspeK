package ar.com.dgarcia.javaspec.api;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;

import java.util.List;

/**
 * This type implements the Java Spec runner needed to extend Junit running mechanism
 * Created by kfgodel on 12/07/14.
 */
public class JavaSpecRunner extends Suite {

    protected JavaSpecRunner(Class<?> klass, List<Runner> runners) throws InitializationError {
        super(klass, runners);
    }
}

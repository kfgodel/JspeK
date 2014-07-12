package ar.com.dgarcia.javaspec;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

/**
 * This type implements the Java Spec runner needed to extend Junit running mechanism
 * Created by kfgodel on 12/07/14.
 */
public class JavaSpecRunner extends Runner {

    @Override
    public Description getDescription() {
        return null;
    }

    @Override
    public void run(RunNotifier runNotifier) {

    }
}

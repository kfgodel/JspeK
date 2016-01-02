package ar.com.dgarcia.javaspec.impl.junit;

import org.junit.internal.runners.model.EachTestNotifier;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;

/**
 * This type represents a test code to be ignored by Junit
 * Created by kfgodel on 13/07/14.
 */
public class JunitIgnoredTestCode implements JunitTestCode {

    private Description testDescription;

    /**
     * Executes this test code, notifying the given notifier for state changes
     * @param notifier The notifier for this test
     */
    public void executeNotifying(RunNotifier notifier) {
        EachTestNotifier testNotifier = new EachTestNotifier(notifier, testDescription);
        testNotifier.fireTestIgnored();
    }

    public static JunitIgnoredTestCode create(Description testDescription) {
        JunitIgnoredTestCode junitTestCode = new JunitIgnoredTestCode();
        junitTestCode.testDescription = testDescription;
        return junitTestCode;
    }

    public Description getTestDescription() {
        return testDescription;
    }
}

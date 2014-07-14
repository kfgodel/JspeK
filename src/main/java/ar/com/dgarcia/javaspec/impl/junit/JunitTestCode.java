package ar.com.dgarcia.javaspec.impl.junit;

import org.junit.internal.AssumptionViolatedException;
import org.junit.internal.runners.model.EachTestNotifier;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;

/**
 * This type represents a test code to be tested with Junit
 * Created by kfgodel on 13/07/14.
 */
public class JunitTestCode {

    private Runnable testCode;
    private Description testDescription;
    private boolean mustIgnore;

    /**
     * Executes this test code, notifying the given notifier for state changes
     * @param notifier The notifier for this test
     */
    public void executeNotifying(RunNotifier notifier) {
        EachTestNotifier testNotifier = new EachTestNotifier(notifier, testDescription);
        if (mustIgnore) {
            testNotifier.fireTestIgnored();
            return;
        }
        testNotifier.fireTestStarted();
        try {
            testCode.run();
        } catch (AssumptionViolatedException e) {
            testNotifier.addFailedAssumption(e);
        } catch (Throwable e) {
            testNotifier.addFailure(e);
        } finally {
            testNotifier.fireTestFinished();
        }
    }

    public static JunitTestCode create(Runnable testCode, Description testDescription) {
        JunitTestCode junitTestCode = new JunitTestCode();
        junitTestCode.testCode = testCode;
        junitTestCode.testDescription = testDescription;
        junitTestCode.mustIgnore = false;
        return junitTestCode;
    }

    public void ignoreTest(){
        this.mustIgnore = true;
    }

    public Description getTestDescription() {
        return testDescription;
    }
}

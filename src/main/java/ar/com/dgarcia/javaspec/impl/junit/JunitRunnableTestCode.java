package ar.com.dgarcia.javaspec.impl.junit;

import org.junit.internal.AssumptionViolatedException;
import org.junit.internal.runners.model.EachTestNotifier;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;

/**
 * This type represents a test code to be tested with Junit
 * Created by kfgodel on 13/07/14.
 */
public class JunitRunnableTestCode implements JunitTestCode {

    private Runnable testCode;
    private Description testDescription;

    /**
     * Executes this test code, notifying the given notifier for state changes
     * @param notifier The notifier for this test
     */
    public void executeNotifying(RunNotifier notifier) {
        EachTestNotifier testNotifier = new EachTestNotifier(notifier, testDescription);
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

    public static JunitRunnableTestCode create(Runnable testCode, Description testDescription) {
        JunitRunnableTestCode junitRunnableTestCode = new JunitRunnableTestCode();
        junitRunnableTestCode.testCode = testCode;
        junitRunnableTestCode.testDescription = testDescription;
        return junitRunnableTestCode;
    }

    public Description getTestDescription() {
        return testDescription;
    }
}

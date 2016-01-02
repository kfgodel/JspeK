package ar.com.dgarcia.javaspec.impl.junit;

import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;

/**
 * This type represents a junit adapted test according to a spec definition.
 * Created by tenpines on 31/12/15.
 */
public interface JunitTestCode {

  /**
   * Executes this test code, notifying the given notifier for state changes
   * @param notifier The notifier for this test
   */
  void executeNotifying(RunNotifier notifier);

  /**
   * @return Describes this test for junit
   */
  Description getTestDescription();
}

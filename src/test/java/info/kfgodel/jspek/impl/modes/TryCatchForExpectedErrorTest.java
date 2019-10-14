package info.kfgodel.jspek.impl.modes;

import info.kfgodel.jspek.api.exceptions.FailingRunnable;
import info.kfgodel.jspek.api.variable.Variable;
import org.junit.Test;

import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;


/**
 * Date: 14/10/19 - 17:20
 */
public class TryCatchForExpectedErrorTest {

  @Test
  public void itFailsTheAssertionIfTheCodeDoesntFail() {
    FailingRunnable<Exception> nonFailingCode = () -> {
    };
    Consumer<Exception> irrelevantConsumer = (a) -> {
    };

    try {
      DefinitionMode.tryCatchForExpectedError(nonFailingCode, Exception.class,
        irrelevantConsumer, "an exception name");
    } catch (AssertionError e) {
      assertThat(e).hasMessage("No exception thrown while expecting: an exception name");
    }
  }

  @Test
  public void itRethrowsAnyAssertionError() {
    FailingRunnable<Exception> failedAssertionCode = () -> {
      fail("original assertion fail");
    };
    Consumer<Exception> irrelevantConsumer = (a) -> {
    };

    try {
      DefinitionMode.tryCatchForExpectedError(failedAssertionCode, Exception.class,
        irrelevantConsumer, "an exception name");
    } catch (AssertionError e) {
      assertThat(e).hasMessage("original assertion fail");
    }
  }

  @Test
  public void itGeneratesAnAssertionErrorIfErrorIsNotFromExpectedType() {
    FailingRunnable<NullPointerException> failedAssertionCode = () -> {
      throw new IllegalArgumentException("original error");
    };
    Consumer<Exception> irrelevantConsumer = (a) -> {
    };
    try {
      DefinitionMode.tryCatchForExpectedError(failedAssertionCode, NullPointerException.class,
        irrelevantConsumer, "a null pointer");
    } catch (AssertionError e) {
      assertThat(e).hasMessage("Caught java.lang.IllegalArgumentException: original error while expecting a null pointer");
    }
  }

  @Test
  public void itPassesTheExpectedErrorToItsConsumerWhenThrown() {
    FailingRunnable<IllegalArgumentException> failedAssertionCode = () -> {
      throw new IllegalArgumentException("original error");
    };
    final Variable<Exception> catchedError = Variable.create();
    DefinitionMode.tryCatchForExpectedError(failedAssertionCode, Exception.class,
      catchedError::set, "an exception name");
    assertThat(catchedError.get()).hasMessage("original error");
  }

}
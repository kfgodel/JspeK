package info.kfgodel.jspek.junit;

import info.kfgodel.jspek.impl.junit.JunitTestCode;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.AssumptionViolatedException;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * This type verifyes that JunitTestCode behaves as expected to junit
 * Created by kfgodel on 13/07/14.
 */
public class JunitTestCodeTest {

    private RunNotifier mockedNotifier;
    private Description mockedDescription;
    private Runnable mockedCode;
    private JunitTestCode junitTest;

    @Before
    public void prepareTestDoubles(){
        mockedNotifier = mock(RunNotifier.class);
        mockedDescription = mock(Description.class);
        mockedCode = mock(Runnable.class);
        junitTest = JunitTestCode.create(mockedCode, mockedDescription);
    }

    @Test
    public void notifiesWhenTestStarts(){
        junitTest.executeNotifying(mockedNotifier);

        verify(mockedNotifier).fireTestStarted(mockedDescription);
    }

    @Test
    public void notifiesWhenTestEnds(){
        junitTest.executeNotifying(mockedNotifier);

        verify(mockedNotifier).fireTestFinished(mockedDescription);
    }

    @Test
    public void notifiesWhenTestBreaks() {
        doAnswer(invocationOnMock -> {
            throw new RuntimeException("Breaking bad");
        }).when(mockedCode).run();

        junitTest.executeNotifying(mockedNotifier);

        ArgumentCaptor<Failure> argument = ArgumentCaptor.forClass(Failure.class);
        verify(mockedNotifier).fireTestFailure(argument.capture());
        assertThat(argument.getValue().getMessage()).isEqualTo("Breaking bad");
    }

    @Test
    public void notifiesWhenTestIsNonCompliant(){
        doAnswer(invocationOnMock -> {
            throw new AssumptionViolatedException("non compliant");
        }).when(mockedCode).run();

        junitTest.executeNotifying(mockedNotifier);

        verify(mockedNotifier).fireTestAssumptionFailed(any());
    }

    @Test
    public void notifiesWhenTestIsIgnored(){
        doAnswer(invocationOnMock -> {
            throw new RuntimeException("Shouldn't execute");
        }).when(mockedCode).run();

        junitTest.ignoreTest();
        junitTest.executeNotifying(mockedNotifier);

        verify(mockedNotifier).fireTestIgnored(mockedDescription);
    }
}

package ar.com.dgarcia.javaspec.junit;

import ar.com.dgarcia.javaspec.impl.junit.JunitIgnoredTestCode;
import ar.com.dgarcia.javaspec.impl.junit.JunitRunnableTestCode;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.AssumptionViolatedException;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.mockito.ArgumentMatcher;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.*;

/**
 * This type verifyes that JunitTestCode behaves as expected to junit
 * Created by kfgodel on 13/07/14.
 */
public class JunitTestCodeRunnableTest {

    private RunNotifier mockedNotifier;
    private Description mockedDescription;
    private Runnable mockedCode;
    private JunitRunnableTestCode junitTest;
    private JunitIgnoredTestCode ignoredJunitTest;

    @Before
    public void prepareTestDoubles(){
        mockedNotifier = mock(RunNotifier.class);
        mockedDescription = mock(Description.class);
        mockedCode = mock(Runnable.class);
        junitTest = JunitRunnableTestCode.create(mockedCode, mockedDescription);
        ignoredJunitTest = JunitIgnoredTestCode.create(mockedDescription);
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
            throw new RuntimeException("Blowing in the air");
        }).when(mockedCode).run();

        junitTest.executeNotifying(mockedNotifier);

        verify(mockedNotifier).fireTestFailure(argThat(new ArgumentMatcher<Failure>() {
            @Override
            public boolean matches(Object o) {
                Failure failure = (Failure) o;
                return failure.getMessage().equals("Blowing in the air");
            }
        }));
    }

    @Test
    public void notifiesWhenTestIsNonCompliant(){
        doAnswer(invocationOnMock -> {
            throw new AssumptionViolatedException("non compliant");
        }).when(mockedCode).run();

        junitTest.executeNotifying(mockedNotifier);

        verify(mockedNotifier).fireTestAssumptionFailed(anyObject());
    }

    @Test
    public void notifiesWhenTestIsIgnored(){
        ignoredJunitTest.executeNotifying(mockedNotifier);

        verify(mockedNotifier).fireTestIgnored(mockedDescription);
    }
}

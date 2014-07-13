package ar.com.dgarcia.javaspec.impl.model;

import java.util.List;

/**
 * This type represents a single test to be run in a spec
 * Created by kfgodel on 12/07/14.
 */
public interface SpecTest extends SpecElement {
    /**
     * Returns the name that identifies this test
     * @return The name declared by it
     */
    String getName();

    /**
     * Indicates if this test has a pending status because of its declaration
     * @return true if pending
     */
    boolean isMarkedAsPending();


    /**
     * Returns the code that defines this test execution.<br>
     *     Code may be null if this is a pending test
     * @return The code to run for this test
     */
    Runnable getTestCode();

    /**
     * Marks this test spec as pending.<br>
     *     It will be listed but not executed
     */
    void markAsPending();
}

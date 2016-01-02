package ar.com.dgarcia.javaspec.impl.model;


import java.util.Optional;

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
     * Returns the code that is particular to this test as defined by the user.<br>
     *     Code may be not be available if this is a pending test
     * @return The code to run for this test
     */
    Optional<Runnable> getTestBodyCode();

    /**
     * Marks this test spec as pending.<br>
     *     It will be listed but not executed
     */
    void markAsPending();

    /**
     * Returns a code block to be run as the full spec. Including before and after code blocks as well as test code,
     * as a single runnable unit.
     * @return The code to execute as a complete spec test
     */
    Optional<Runnable> getFullExecutionCode();
}

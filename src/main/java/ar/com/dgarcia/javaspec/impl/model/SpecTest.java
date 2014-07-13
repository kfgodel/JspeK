package ar.com.dgarcia.javaspec.impl.model;

import java.util.List;

/**
 * This type represents a single test to be run in a spec
 * Created by kfgodel on 12/07/14.
 */
public interface SpecTest {
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
     * Returns the ordered runnables that represent code blocks to execute before to the test.<br>
     *     Inherited blocks are first
     * @return The list of inherited before blocks
     */
    List<Runnable> getBeforeBlocks();

    /**
     * Returns the ordered runnables that represent code blocks to execute after the test.<br>
     *     Inherited blocks are last
     * @return The list of inherited after blocks
     */
    List<Runnable> getAfterBlocks();
}

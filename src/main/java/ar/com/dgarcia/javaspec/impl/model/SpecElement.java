package ar.com.dgarcia.javaspec.impl.model;

import ar.com.dgarcia.javaspec.impl.model.impl.PendingStatus;

import java.util.List;

/**
 * This type represents a java spec element that has a name
 * Created by kfgodel on 12/07/14.
 */
public interface SpecElement {

    /**
     * The name identifying this element
     * @return The name given by definition of this element
     */
    public String getName();

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

package ar.com.dgarcia.javaspec.impl.model;

/**
 * This type represents the specification of tests defined in one subclass of JavaSpec
 * Created by kfgodel on 12/07/14.
 */
public interface SpecTree {
    /**
     * Indicates if this tree contains any spec test.<br>
     * @return true if there's no spec in this tree
     */
    boolean isEmpty();

    /**
     * Return the root annonymous group of the spec
     * @return
     */
    SpecGroup getRootGroup();
}

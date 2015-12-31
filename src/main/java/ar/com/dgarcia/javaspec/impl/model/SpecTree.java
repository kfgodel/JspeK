package ar.com.dgarcia.javaspec.impl.model;

/**
 * This type represents the specification of tests defined in one subclass of JavaSpec
 * Created by kfgodel on 12/07/14.
 */
public interface SpecTree {
    /**
     * Indicates if this tree contains any spec test.<br>
     * @return true if there's no test to be run on this tree
     */
    boolean hasNoTests();

    /**
     * Return the root annonymous group of the spec
     * @return
     */
    SpecGroup getRootGroup();

  /**
   * The class in which this tree is defined
   */
  Class<?> getDefiningClass();

}

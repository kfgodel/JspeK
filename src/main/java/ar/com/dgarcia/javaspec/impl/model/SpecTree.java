package ar.com.dgarcia.javaspec.impl.model;

import ar.com.dgarcia.javaspec.api.TestContext;
import ar.com.dgarcia.javaspec.api.Variable;

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
     * Variable shared between tests to replace context instance on test execution
     * @return The variable used to define active context in each test
     */
    Variable<TestContext> getSharedContext();
}

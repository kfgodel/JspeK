package ar.com.dgarcia.javaspec.impl.junit;

import org.junit.runner.Description;

import java.util.ArrayList;
import java.util.List;

/**
 * This type represents a tree of test to be run with junit. Leafs are tests and parents are test groups
 * Created by kfgodel on 13/07/14.
 */
public class JunitTestTree {

    private Description rootDescription;
    private List<JunitTestCode> tests;

    /**
     * Returns the description tree built from the spec tree
     * @return The tree emulating the spec tree names
     */
    public Description getJunitDescription() {
        return rootDescription;
    }

    /**
     * Returns the created test from each spec test
     * @return The list of test to be used with junit
     */
    public List<JunitTestCode> getJunitTests() {
        return tests;
    }

    public static JunitTestTree create(String treeName) {
        JunitTestTree testTree = new JunitTestTree();
        testTree.rootDescription = Description.createSuiteDescription(treeName);
        testTree.tests = new ArrayList<>();
        return testTree;
    }

    /**
     * Adds the given test to this test tree
     * @param junitTest The test to run in this tree
     */
    public void addTest(JunitTestCode junitTest) {
        this.tests.add(junitTest);
    }

}

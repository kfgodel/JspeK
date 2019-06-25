package info.kfgodel.jspek.impl.model;

import info.kfgodel.jspek.impl.model.impl.GroupSpecDefinition;
import info.kfgodel.jspek.impl.model.impl.TestSpecDefinition;

import java.util.List;

/**
 * This type represents a test group inside a spec
 * Created by kfgodel on 12/07/14.
 */
public interface SpecGroup extends SpecElement {
    /**
     * Indicates if this group contains any spec test.<br>
     * @return true if there's no spec in this group
     */
    boolean isEmpty();

    /**
     * Returns the ordered list of declared groups inside this one
     * @return The list of this sub-groups
     */
    List<SpecGroup> getSubGroups();

    /**
     * Returns the name identifying this group
     * @return The name given by describe
     */
    String getName();

    /**
     * Returns the ordered list of tests declared directly inside the body of this group
     * @return The test list
     */
    List<SpecTest> getDeclaredTests();

    /**
     * Indicates that this group is disabled as all its childs
     * @return true if this group is disabled
     */
    boolean isMarkedAsDisabled();

    /**
     * Disables this group and its childs
     */
    void markAsDisabled();

    /**
     * Adds a new group definition to this group.<br>
     * @param addedGroup The group to add as child of this
     */
    void addSubGroup(GroupSpecDefinition addedGroup);

    /**
     * Adds a new test spec to this group
     * @param addedSpec The test to add as child of this group
     */
    void addTest(TestSpecDefinition addedSpec);

    /**
     * Adds a before block to this group definition. Every test on this group, and every subgroup will execute it
     * @param aCodeBlock The code to execute before tests
     */
    void addBeforeBlock(Runnable aCodeBlock);

    /**
     * Adds an after block to this group definition. Every test on this group and every subgroup will execute it.
     *
     * @param aCodeBlock The code to execute after tests
     */
    void addAfterBlock(Runnable aCodeBlock);

    /**
     * Returns all the elements in the order declared in this group.<br>
     *     Elements will be SpecGroup or SpecTest
     * @return The list of declared elemetns
     */
    List<SpecElement> getSpecElements();

    /**
     * Indicates if this group has no test directly or indirectly by its children
     * @return true if this group has no test. False if it has, even if disabled
     */
    boolean hasNoTests();

    /**
     * Returns the context definition in the scope of this group.<br>
     * The context returned can re-define some of the variables from parent context
     * @return The context for this group
     */
    TestContextDefinition getTestContext();
}

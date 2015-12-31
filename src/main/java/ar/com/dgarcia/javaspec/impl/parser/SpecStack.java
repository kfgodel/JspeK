package ar.com.dgarcia.javaspec.impl.parser;

import ar.com.dgarcia.javaspec.api.TestContext;
import ar.com.dgarcia.javaspec.api.Variable;
import ar.com.dgarcia.javaspec.impl.model.SpecGroup;
import ar.com.dgarcia.javaspec.impl.model.TestContextDefinition;
import ar.com.dgarcia.javaspec.impl.model.impl.GroupSpecDefinition;

import java.util.LinkedList;

/**
 * This type represents the active stack used to define nesting of specs
 * Created by kfgodel on 12/07/14.
 */
public class SpecStack {

    private LinkedList<SpecGroup> nestedGroups;
    private Variable<TestContext> currentContext;

    public static SpecStack create(SpecGroup rootGroup, Variable<TestContext> sharedContext) {
        SpecStack stack = new SpecStack();
        stack.nestedGroups = new LinkedList<>();
        stack.currentContext = sharedContext;
        stack.push(rootGroup);
        return stack;
    }

    /**
     * Returns the current head of the stack
     * @return The group that represents the current active group
     */
    public SpecGroup getCurrentGroup() {
        return this.nestedGroups.peek();
    }

    /**
     * Pushes the passed group as current stack head and executes the given code.<br>
     *     Afters execution finishes (with or without error) removes the group and restores previous head
     * @param newHead The group to used a stack head
     * @param code The code to execute with the newHead
     */
    public void runNesting(GroupSpecDefinition newHead, Runnable code) {
      this.getCurrentGroup().addSubElement(newHead);
      this.push(newHead);
      try{
          code.run();
      }finally {
          this.pop();
      }
    }

    private void push(SpecGroup group) {
        this.nestedGroups.push(group);
        this.updateCurrentContext();
    }

    /**
     * Grabs the context from current head and sets that as current context
     */
    private void updateCurrentContext() {
        TestContextDefinition currentGroupContext = getCurrentGroup().getTestContext();
        currentContext.set(currentGroupContext);
    }

    private void pop() {
        this.nestedGroups.pop();
        this.updateCurrentContext();
    }

}

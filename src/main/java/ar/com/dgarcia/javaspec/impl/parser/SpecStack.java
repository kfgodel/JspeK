package ar.com.dgarcia.javaspec.impl.parser;

import ar.com.dgarcia.javaspec.impl.model.SpecGroup;
import ar.com.dgarcia.javaspec.impl.model.impl.GroupSpecDefinition;

import java.util.LinkedList;

/**
 * This type represents the active stack used to define nesting of specs
 * Created by kfgodel on 12/07/14.
 */
public class SpecStack {

    private LinkedList<SpecGroup> nestedGroups;

    public static SpecStack create(SpecGroup rootGroup) {
        SpecStack stack = new SpecStack();
        stack.nestedGroups = new LinkedList<>();
        stack.push(rootGroup);
        return stack;
    }

    private void push(SpecGroup group) {
        this.nestedGroups.push(group);
    }

    /**
     * Pushes the passed group as current stack head and executes the given code.<br>
     *     Afters execution finishes (with or without error) removes the group and restores previous head
     * @param newHead The group to used a stack head
     * @param code The code to execute with the newHead
     */
    public void executeAsCurrent(GroupSpecDefinition newHead, Runnable code) {
        this.push(newHead);
        try{
            code.run();
        }finally {
            this.pop();
        }
    }

    private void pop() {
        this.nestedGroups.pop();
    }

    /**
     * Returns the current head of the stack
     * @return The group that represents the current active group
     */
    public SpecGroup getCurrentHead() {
        return this.nestedGroups.peek();
    }
}

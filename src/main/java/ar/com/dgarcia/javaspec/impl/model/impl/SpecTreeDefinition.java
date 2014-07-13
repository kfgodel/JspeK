package ar.com.dgarcia.javaspec.impl.model.impl;

import ar.com.dgarcia.javaspec.impl.model.SpecGroup;
import ar.com.dgarcia.javaspec.impl.model.SpecTree;

/**
 * This type represents the tree defined by a java spec
 * Created by kfgodel on 12/07/14.
 */
public class SpecTreeDefinition implements SpecTree {

    private SpecGroup rootGroup;

    @Override
    public boolean hasNoTests() {

        return rootGroup.isEmpty();
    }

    @Override
    public SpecGroup getRootGroup() {
        return rootGroup;
    }

    public static SpecTreeDefinition create() {
        SpecTreeDefinition tree = new SpecTreeDefinition();
        tree.rootGroup = GroupSpecDefinition.create("anonymous root");
        return tree;
    }
}

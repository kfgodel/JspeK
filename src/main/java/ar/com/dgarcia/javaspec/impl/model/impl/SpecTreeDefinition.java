package ar.com.dgarcia.javaspec.impl.model.impl;

import ar.com.dgarcia.javaspec.impl.model.SpecGroup;
import ar.com.dgarcia.javaspec.impl.model.SpecTree;

/**
 * This type represents the tree defined by a java spec
 * Created by kfgodel on 12/07/14.
 */
public class SpecTreeDefinition implements SpecTree {

    private SpecGroup rootGroup;

    public Class<?> getDefiningClass() {
        return definingClass;
    }

    private Class<?> definingClass;

    @Override
    public boolean hasNoTests() {
        return rootGroup.hasNoTests();
    }

    @Override
    public SpecGroup getRootGroup() {
        return rootGroup;
    }

    public static SpecTreeDefinition create(Class<?> definingClass) {
        SpecTreeDefinition tree = new SpecTreeDefinition();
        tree.definingClass = definingClass;
        String rootGroupName = definingClass.getSimpleName() + " root";
        tree.rootGroup = GroupSpecDefinition.create(rootGroupName);
        return tree;
    }

}

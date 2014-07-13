package ar.com.dgarcia.javaspec.impl.model.impl;

import ar.com.dgarcia.javaspec.impl.model.SpecElement;
import ar.com.dgarcia.javaspec.impl.model.SpecGroup;

/**
 * This class serves as base class defining common spec element behavior
 * Created by kfgodel on 12/07/14.
 */
public abstract class SpecElementSupport implements SpecElement {

    private String name;
    private SpecGroup containerGroup;

    protected void setName(String newName){
        this.name = newName;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public SpecGroup getContainerGroup() {
        return containerGroup;
    }

    protected void setContainerGroup(SpecGroup containerGroup) {
        this.containerGroup = containerGroup;
    }


    @Override
    public String toString() {
        return getClass().getSimpleName() + ": " + getName();
    }
}

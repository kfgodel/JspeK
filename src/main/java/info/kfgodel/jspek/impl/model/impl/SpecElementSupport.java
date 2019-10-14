package info.kfgodel.jspek.impl.model.impl;

import info.kfgodel.jspek.impl.model.SpecElement;
import info.kfgodel.jspek.impl.model.SpecGroup;

/**
 * This class serves as base class defining common spec element behavior
 * Created by kfgodel on 12/07/14.
 */
public abstract class SpecElementSupport implements SpecElement {

  private String name;
  private SpecGroup containerGroup;

  protected void setName(String newName) {
    if (newName == null || newName.trim().isEmpty()) {
      throw new IllegalArgumentException("Empty string cannot be used with it() or describe() " +
        "because Junit doesn't support it");
    }
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

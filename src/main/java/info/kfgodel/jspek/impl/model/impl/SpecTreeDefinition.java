package info.kfgodel.jspek.impl.model.impl;

import info.kfgodel.jspek.api.contexts.TestContext;
import info.kfgodel.jspek.api.variable.Variable;
import info.kfgodel.jspek.impl.model.SpecGroup;
import info.kfgodel.jspek.impl.model.SpecTree;

/**
 * This type represents the tree defined by a java spec
 * Created by kfgodel on 12/07/14.
 */
public class SpecTreeDefinition implements SpecTree {

  private SpecGroup rootGroup;
  private Variable<TestContext> sharedContext;

  @Override
  public boolean hasNoTests() {
    return rootGroup.hasNoTests();
  }

  @Override
  public SpecGroup getRootGroup() {
    return rootGroup;
  }

  public static SpecTreeDefinition create() {
    SpecTreeDefinition tree = new SpecTreeDefinition();
    tree.rootGroup = GroupSpecDefinition.create("anonymous root");
    tree.sharedContext = Variable.create();
    return tree;
  }

  public Variable<TestContext> getSharedContext() {
    return sharedContext;
  }
}

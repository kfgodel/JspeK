package ar.com.dgarcia.javaspec.impl.model.impl;

import ar.com.dgarcia.javaspec.api.contexts.TestContext;
import ar.com.dgarcia.javaspec.api.variable.Variable;
import ar.com.dgarcia.javaspec.impl.model.SpecGroup;
import ar.com.dgarcia.javaspec.impl.model.SpecTree;
import ar.com.dgarcia.javaspec.impl.parser.SpecStack;

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
    tree.sharedContext = Variable.of(tree.rootGroup.getTestContext());
    return tree;
  }

  public Variable<TestContext> getSharedContext() {
    return sharedContext;
  }

  @Override
  public SpecStack createStack() {
    SpecGroup rootGroup = this.getRootGroup();
    Variable<TestContext> sharedContext = this.getSharedContext();
    return SpecStack.create(rootGroup, sharedContext);
  }
}

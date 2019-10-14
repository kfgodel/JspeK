package info.kfgodel.jspek.impl.junit;

import info.kfgodel.jspek.api.JavaSpec;
import info.kfgodel.jspek.api.exceptions.SpecException;
import info.kfgodel.jspek.impl.model.SpecGroup;
import info.kfgodel.jspek.impl.model.SpecTest;
import info.kfgodel.jspek.impl.model.SpecTree;
import org.junit.runner.Description;

import java.lang.annotation.Annotation;

/**
 * This type adapts the SpecTree to a Junit test tree by giving it a description and surrounding each test spec in a JunitTestCode
 * Created by kfgodel on 13/07/14.
 */
public class JunitTestTreeAdapter {

  public static final Annotation[] NO_ANNOTATIONS = new Annotation[0];

  private SpecTree specTree;
  private JunitTestTree junitTree;

  public static JunitTestTreeAdapter create(SpecTree specTree, Class<? extends JavaSpec> clase) {
    JunitTestTreeAdapter adapter = new JunitTestTreeAdapter();
    adapter.specTree = specTree;
    adapter.adaptToJunit(clase);
    return adapter;
  }

  /**
   * Creates a junit test tree with the spec tree
   *
   * @param clase
   */
  private void adaptToJunit(Class<? extends JavaSpec> clase) {
    Description rootDescription = Description.createSuiteDescription(clase);
    junitTree = JunitTestTree.create(rootDescription);
    SpecGroup rootGroup = specTree.getRootGroup();
    recursiveAdaptToJunit(rootGroup, rootDescription);
  }

  private void recursiveAdaptToJunit(SpecGroup currentGroup, Description currentDescription) {
    currentGroup.getSpecElements().forEach(specElement -> {
      String specName = specElement.getName();
      String specId = specName + specElement.hashCode();
      Description elementDescription = Description.createSuiteDescription(specName, specId, NO_ANNOTATIONS);
      currentDescription.addChild(elementDescription);

      if (specElement instanceof SpecTest) {
        JunitTestCode junitTest = adaptToJunitTest((SpecTest) specElement, elementDescription);
        this.junitTree.addTest(junitTest);
      } else if (specElement instanceof SpecGroup) {
        SpecGroup specGroup = (SpecGroup) specElement;
        recursiveAdaptToJunit(specGroup, elementDescription);
      } else {
        throw new SpecException("There's a new SpecElement that this code is unaware of");
      }
    });
  }

  private JunitTestCode adaptToJunitTest(SpecTest specTest, Description elementDescription) {
    Runnable specTestCode = specTest.getSpecExecutionCode();
    JunitTestCode junitTest = JunitTestCode.create(specTestCode, elementDescription);
    if (specTest.isMarkedAsPending()) {
      junitTest.ignoreTest();
    }
    return junitTest;
  }

  public JunitTestTree getJunitTree() {
    return junitTree;
  }

  public SpecTree getSpecTree() {
    return specTree;
  }
}

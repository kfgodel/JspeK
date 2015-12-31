package ar.com.dgarcia.javaspec.impl.junit;

import ar.com.dgarcia.javaspec.impl.model.SpecElement;
import ar.com.dgarcia.javaspec.impl.model.SpecGroup;
import ar.com.dgarcia.javaspec.impl.model.SpecTest;
import ar.com.dgarcia.javaspec.impl.model.SpecTree;
import org.junit.runner.Description;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;

/**
 * This type adapts the SpecTree to a Junit test tree by giving it a description and surrounding each test spec in a JunitTestCode
 * Created by kfgodel on 13/07/14.
 */
public class JunitTestTreeAdapter {

    public static final Annotation[] NO_ANNOTATIONS = new Annotation[0];

    private SpecTree specTree;
    private JunitTestTree junitTree;

    public static JunitTestTreeAdapter create(SpecTree specTree) {
        JunitTestTreeAdapter adapter = new JunitTestTreeAdapter();
        adapter.specTree = specTree;
        adapter.adaptToJunit();
        return adapter;
    }

    /**
     * Creates a junit test tree with the spec tree
     */
    private void adaptToJunit() {
        Description rootDescription = Description.createSuiteDescription(specTree.getDefiningClass());
        junitTree = JunitTestTree.create(rootDescription);

        SpecGroup rootGroup = specTree.getRootGroup();
        adaptGroupToJunit(rootGroup, rootDescription);
    }

    private void adaptGroupToJunit(SpecGroup currentGroup, Description currentDescription) {
        List<SpecElement> specElements = currentGroup.getSpecElements();
        for (SpecElement specElement : specElements) {
            adaptElementToJunit(currentDescription, specElement);
        }
    }

    private void adaptElementToJunit(Description parentDescription, SpecElement specElement) {
      Description elementDescription = createElementDescription(specElement);
      parentDescription.addChild(elementDescription);

      if (specElement instanceof SpecTest) {
        adaptTestToJunit((SpecTest) specElement, elementDescription);
      }

      if (specElement instanceof SpecGroup) {
        adaptGroupToJunit((SpecGroup) specElement, elementDescription);
      }
    }

  private Description createElementDescription(SpecElement specElement) {
    String specName = specElement.getName();
    String specId = specName + String.valueOf(specElement.hashCode());
    return Description.createSuiteDescription(specName, specId, NO_ANNOTATIONS);
  }

  private void adaptTestToJunit(SpecTest specTest, Description elementDescription) {
    Optional<Runnable> fullTestCode = specTest.getFullExecutionCode();
    // Convert the spec code to a junit equivalent, taking into account if it's marked as ignored
    JunitTestCode junitTest = fullTestCode
      .map((runnable) -> (JunitTestCode) JunitRunnableTestCode.create(runnable, elementDescription))
      .orElse(JunitIgnoredTestCode.create(elementDescription));
    this.junitTree.addTest(junitTest);
  }

    public JunitTestTree getJunitTree() {
        return junitTree;
    }
}

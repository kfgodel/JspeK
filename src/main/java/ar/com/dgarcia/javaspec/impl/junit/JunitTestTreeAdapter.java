package ar.com.dgarcia.javaspec.impl.junit;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.impl.model.SpecElement;
import ar.com.dgarcia.javaspec.impl.model.SpecGroup;
import ar.com.dgarcia.javaspec.impl.model.SpecTest;
import ar.com.dgarcia.javaspec.impl.model.SpecTree;
import org.junit.runner.Description;

import java.lang.annotation.Annotation;
import java.util.List;

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
     * @param clase
     */
    private void adaptToJunit(Class<? extends JavaSpec> clase) {
    	Description rootDescription = Description.createSuiteDescription(clase);
        junitTree = JunitTestTree.create(rootDescription);
        SpecGroup rootGroup = specTree.getRootGroup();
        recursiveAdaptToJunit(rootGroup, rootDescription);
    }

    private void recursiveAdaptToJunit(SpecGroup currentGroup, Description currentDescription) {
        List<SpecElement> specElements = currentGroup.getSpecElements();
        for (SpecElement specElement : specElements) {

            String specName = specElement.getName();
            String specId = specName + String.valueOf(specElement.hashCode());
            Description elementDescription = Description.createSuiteDescription(specName, specId, NO_ANNOTATIONS);
            currentDescription.addChild(elementDescription);

            if(specElement instanceof SpecTest){
                JunitTestCode junitTest = adaptToJunitTest((SpecTest) specElement, elementDescription);
                this.junitTree.addTest(junitTest);
            }

            if(specElement instanceof SpecGroup){
                SpecGroup specGroup = (SpecGroup) specElement;
                recursiveAdaptToJunit(specGroup, elementDescription);
            }
        }
    }

    private JunitTestCode adaptToJunitTest(SpecTest specTest, Description elementDescription) {
        Runnable specTestCode = specTest.getSpecExecutionCode();
        JunitTestCode junitTest = JunitTestCode.create(specTestCode, elementDescription);
        if(specTest.isMarkedAsPending()){
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

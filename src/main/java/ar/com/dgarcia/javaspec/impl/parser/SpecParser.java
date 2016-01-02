package ar.com.dgarcia.javaspec.impl.parser;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.dgarcia.javaspec.api.JavaSpecTestable;
import ar.com.dgarcia.javaspec.impl.exceptions.SpecException;
import ar.com.dgarcia.javaspec.impl.model.SpecTree;

/**
 * This type defines the parser that understands the definition of a JavaSpec subclass, and creates a model of the specs
 * Created by kfgodel on 12/07/14.
 */
public class SpecParser {

    public static SpecParser create() {
        SpecParser parser = new SpecParser();
        return parser;
    }

    public SpecTree parse(Class<?> specClass) throws IllegalArgumentException {
        // Usage validation check
        if(!JavaSpecTestable.class.isAssignableFrom(specClass)) {
            throw new IllegalArgumentException("Your class["+specClass+"] must extend " + JavaSpec.class + " or implement "+JavaSpecTestable.class+" to be run with " +  JavaSpecRunner.class.getSimpleName());
        }
        JavaSpecTestable specInstance = instantiate((Class<? extends JavaSpecTestable>) specClass);
        return interpretUserCalls(specInstance);
    }

  /**
   * Generates a tree of specs interpreted out of the user calls to our api methods.<br>
   *   Nested method calls generate groups of test, that end up in branches of the tree
   * @param specInstance The spec definition object defined by user code
   * @return The tree interpretation of the tests
   */
    private SpecTree interpretUserCalls(JavaSpecTestable specInstance) {
        DefinitionInterpreter definitionInterpreter = DefinitionInterpreter.create(specInstance.getClass());
        specInstance.defineSpecs(definitionInterpreter);
        SpecTree createdTree = definitionInterpreter.getSpecTree();
        return createdTree;
    }

    /**
     * Creates the new instance using reflection on niladic constructor
     */
    private JavaSpecTestable instantiate(Class<? extends JavaSpecTestable> specClass) {
        try {
            return specClass.newInstance();
        } catch( SecurityException e){
            throw new SpecException("Security forbids instantiation for spec["+specClass+"]",e);
        } catch( ExceptionInInitializerError e){
            throw new SpecException("Constructor failed for new spec["+specClass+"] instance", e);
        } catch (InstantiationException e) {
            throw new SpecException("Error creating the spec["+specClass+"] instance", e);
        } catch (IllegalAccessException e) {
            throw new SpecException("Unable to access spec["+specClass+"] constructor for new instance",e);
        }
    }
}

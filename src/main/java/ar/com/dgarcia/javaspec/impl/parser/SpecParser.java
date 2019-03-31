package ar.com.dgarcia.javaspec.impl.parser;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.exceptions.SpecException;
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

    public SpecTree parse(Class<? extends JavaSpec> specClass) {
        JavaSpec createdSpec = instantiate(specClass);
        return createdSpec.populate();
    }

    /**
     * Creates the new instance using reflection on niladic constructor
     */
    private JavaSpec instantiate(Class<? extends JavaSpec> specClass) {
        try {
            JavaSpec createdInstance = specClass.newInstance();
            return createdInstance;
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

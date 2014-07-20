package ar.com.dgarcia.javaspec.impl.context;

import ar.com.dgarcia.javaspec.impl.exceptions.SpecException;
import ar.com.dgarcia.javaspec.impl.model.TestContextDefinition;

import java.util.function.Supplier;

/**
 * This type is a null implementation of context definition for root contexts
 * Created by kfgodel on 20/07/14.
 */
public class NullContextDefinition implements TestContextDefinition {

    public static NullContextDefinition create() {
        NullContextDefinition definition = new NullContextDefinition();
        return definition;
    }

    @Override
    public void let(String variableName, Supplier<?> valueDefinition) throws SpecException {
        throw new UnsupportedOperationException("Null context cannot define variables: " + variableName);
    }

    @Override
    public <T> T get(String variableName) {
        throw new SpecException("Variable ["+variableName+"] cannot be accessed because lacks definition");
    }
}

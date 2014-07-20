package ar.com.dgarcia.javaspec.impl.context;

import ar.com.dgarcia.javaspec.api.TestContext;
import ar.com.dgarcia.javaspec.impl.exceptions.SpecException;
import ar.com.dgarcia.javaspec.impl.model.TestContextDefinition;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * This type implements a test context that has a parent context
 * Created by kfgodel on 20/07/14.
 */
public class MappedTestContext implements TestContext, TestContextDefinition{

    private TestContextDefinition parentDefinition;
    private Map<String, Supplier<Object>> variableDefinitions;
    private Map<String, Object> variableValues;

    @Override
    public void let(String variableName, Supplier<?> valueDefinition) throws SpecException {
        if(this.containsDefinitionFor(variableName)){
            throw new SpecException("Variable [" + variableName + "] cannot be re-defined. Current definition: ["+get(variableName)+"]");
        }
        this.storeDefinitionFor(variableName, valueDefinition);
    }

    @Override
    public <T> T get(String variableName) {
        if (this.containsValueFor(variableName)) {
            // Use cached value
            return getValueFor(variableName);
        }
        if(!this.containsDefinitionFor(variableName)){
            // Parent can have one
            return parentDefinition.get(variableName);
        }

        Supplier<Object> variableDefinition = getDefinitionFor(variableName);
        Object variableValue = null;
        try {
            variableValue = variableDefinition.get();
        } catch (Exception e) {
            throw new SpecException("Definition for variable ["+variableName+"] failed to execute: " + e.getMessage(),e);
        }
        storeValueFor(variableName, variableValue);
        return (T) variableValue;
    }

    /**
     * Stores the definition for a variable in this context
     * @param variableName The name of the variable
     * @param valueDefinition The variable definition
     */
    private void storeDefinitionFor(String variableName, Supplier<?> valueDefinition) {
        variableDefinitions.put(variableName, (Supplier<Object>) valueDefinition);
    }

    /**
     * Indicates if this context has own definition for named variable
     * @param variableName The variable to check
     * @return true if there's a definition on this context
     */
    private boolean containsDefinitionFor(String variableName) {
        return variableDefinitions.containsKey(variableName);
    }

    private <T> T getValueFor(String variableName) {
        return (T) variableValues.get(variableName);
    }

    private void storeValueFor(String variableName, Object variableValue) {
        variableValues.put(variableName, variableValue);
    }

    private Supplier<Object> getDefinitionFor(String variableName) {
        return variableDefinitions.get(variableName);
    }

    /**
     * Indicates if this context has a value defined for a named variable
     * @param variableName The name of the variable
     * @return true if the variable was already resolved
     */
    private boolean containsValueFor(String variableName) {
        return variableValues.containsKey(variableName);
    }

    public static MappedTestContext create() {
        MappedTestContext context = new MappedTestContext();
        context.variableDefinitions = new HashMap<>();
        context.variableValues = new HashMap<>();
        context.parentDefinition = NullContextDefinition.create();
        return context;
    }

    public void setParentDefinition(TestContextDefinition parentDefinition) {
        this.parentDefinition = parentDefinition;
    }
}

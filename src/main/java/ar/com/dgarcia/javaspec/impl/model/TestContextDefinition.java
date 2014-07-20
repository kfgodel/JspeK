package ar.com.dgarcia.javaspec.impl.model;

import ar.com.dgarcia.javaspec.api.TestContext;

import java.util.function.Supplier;

/**
 * This type represents the definition of a test context
 * Created by kfgodel on 20/07/14.
 */
public interface TestContextDefinition extends TestContext {

    /**
     * Defines the parent context of this instance.<br> Parent context will be used when a definition is not present on current context
     * @param parentDefinition The parent context
     */
    void setParentDefinition(TestContextDefinition parentDefinition);

    /**
     * The parent context of this instance. NullContextDefinition is the root parent
     */
    TestContextDefinition getParentDefinition();

    /**
     * Returns the definition for the named variable in this context
     * @param variableName The name that identifies the variable
     * @return The variable definition or null if there's none in this context
     */
    public Supplier<Object> getDefinitionFor(String variableName);

}

package ar.com.dgarcia.javaspec.api;

import java.util.function.Supplier;

/**
 * This type represents the context specific to a test that can be manipulated to represent different scenarios
 * Created by kfgodel on 18/07/14.
 */
public interface TestContext {
    /**
     * Defines the value to a named variable in the current context, which may redefine previous value of broader context, or be redefined by a subcontext
     * @param variableName The name to identify the variable
     * @param valueDefinition A value supplier that can be used to lazily define the initial value of the variable
     */
    void let(String variableName, Supplier<?> valueDefinition);

    /**
     * Gets the value of the named variable defined in the current context or parent context.<br>
     * The value of the variable is lazily defined the first time accessed. If there's no previous
     * definition of the variable, then an exception will be thrown.
     *
     * @param variableName The variable name which will be accessed (and maybe initialized)
     * @param <T> Type of expected return to avoid casting
     * @return The value of the variable
     */
    <T> T get(String variableName);
}

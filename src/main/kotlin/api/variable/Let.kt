package api.variable

import ar.com.dgarcia.javaspec.api.contexts.TestContext

import java.util.function.Supplier

/**
 * This class allows variable definitions in tests suites that are lazily accessed and can be redefined in subcontexts
 * Created by nrainhart on 15/03/19.
 */
open class Let<out T>(val variableName: String, val context: () -> TestContext) {

    /**
     * Defines the value in the current context, which may redefine previous value of broader context,
     * or be redefined by a subcontext.<br></br> An exception is thrown if a variable is tried to be defined twice in same context
     *
     * @param definition A value supplier that can be used to lazily define the initial value of the variable
     */
    fun <U> set(definition: () -> U): Let<U> {
        context().let(variableName, definition)
        return this as Let<U>
    }

    /**
     * Gets the value defined in the current context or parent context.<br></br>
     * The value of the variable is lazily defined the first time accessed. If there's no previous
     * definition of the variable, then an exception will be thrown.
     *
     * @return The value of the variable
     */
    fun get(): T {
        return context().get(variableName)
    }

}

class UninitializedLet(variableName: String, context: () -> TestContext) : Let<Nothing>(variableName, context)
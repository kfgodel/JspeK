package ar.com.dgarcia.javaspec.impl.context.typed;

import ar.com.dgarcia.javaspec.api.Variable;
import ar.com.dgarcia.javaspec.api.contexts.TestContext;
import ar.com.dgarcia.javaspec.api.exceptions.SpecException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * This type serves as factory to create typed contexts
 * Created by kfgodel on 21/07/14.
 */
public class TypedContextFactory {
    /**
     * Creates a new instance of a proxied typed context to define context variables with a typed interface.<br>
     *     Interface is validated for wrong method definitions
     * @param typedTestContextClass Type of interface for the new instance
     * @param sharedVariable The variable to access current context
     * @param <T> Type of expected instance
     * @return The created instance
     * @throws ar.com.dgarcia.javaspec.api.exceptions.SpecException if there's a validation error with the interface
     */
    public static<T extends TestContext> T createInstanceOf(Class<T> typedTestContextClass, Variable<TestContext> sharedVariable) throws SpecException {
        //We first validate the interface
        TypedContextValidator.create(typedTestContextClass).validate();
        //Only then we create the proxy
        InvocationHandler handler = TypedContextProxyHandler.create(sharedVariable);
        T createdInstance = (T) Proxy.newProxyInstance(typedTestContextClass.getClassLoader(),
                new Class<?>[] { typedTestContextClass },
                handler);
        return createdInstance;
    }
}

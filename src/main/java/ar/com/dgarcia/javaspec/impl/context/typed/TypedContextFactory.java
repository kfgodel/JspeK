package ar.com.dgarcia.javaspec.impl.context.typed;

import ar.com.dgarcia.javaspec.api.TestContext;
import ar.com.dgarcia.javaspec.api.Variable;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * This type serves as factory to create typed contexts
 * Created by kfgodel on 21/07/14.
 */
public class TypedContextFactory {
    /**
     * Creates a new instance of a proxied typed context to define context variables with a typed interface
     * @param typedTestContextClass Type of interface for the new instance
     * @param sharedVariable The variable to access current context
     * @param <T> Type of expected instance
     * @return The created instance
     */
    public static<T extends TestContext> T createInstanceOf(Class<T> typedTestContextClass, Variable<TestContext> sharedVariable) {
        InvocationHandler handler = TypedContextProxyHandler.create(sharedVariable);
        T createdInstance = (T) Proxy.newProxyInstance(typedTestContextClass.getClassLoader(),
                new Class<?>[] { typedTestContextClass },
                handler);
        return createdInstance;
    }
}

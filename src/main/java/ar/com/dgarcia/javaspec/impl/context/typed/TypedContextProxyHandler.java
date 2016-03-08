package ar.com.dgarcia.javaspec.impl.context.typed;

import ar.com.dgarcia.javaspec.api.Variable;
import ar.com.dgarcia.javaspec.api.contexts.TestContext;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * This type implements the invocation handler used in proxies for typed contexts
 * Created by kfgodel on 21/07/14.
 */
public class TypedContextProxyHandler implements InvocationHandler {

    private Variable<TestContext> currentContext;

    public static TypedContextProxyHandler create(Variable<TestContext> sharedVariable) {
        TypedContextProxyHandler handler = new TypedContextProxyHandler();
        handler.currentContext = sharedVariable;
        return handler;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        TypedContextMethodInvocation methodInvocation = TypedContextMethodInvocation.create(method, args);
        Function<TestContext, Object> operation = createOperationBasedOn(methodInvocation);
        return operation.apply(currentContext.get());
    }

    /**
     * Based on the method signature, creates an operation that delegates or defines variables on a context
     * @param methodInvocation The invocation to analyze
     * @return The created operation
     */
    private Function<TestContext, Object> createOperationBasedOn(TypedContextMethodInvocation methodInvocation) {
        if(methodInvocation.canBeHandledByTestContext()){
            return methodInvocation::invokeOn;
        }

        String variableName = methodInvocation.getVariableName();

        Supplier<Object> variableDefinition = methodInvocation.getVariableDefinitionArgument();
        if(variableDefinition != null){
            //It's a let definition
            return (testContext)-> {
                testContext.let(variableName, variableDefinition);
                return null;
            };
        }

        // It's a get operation
        return (testContext)-> testContext.get(variableName);
    }
}

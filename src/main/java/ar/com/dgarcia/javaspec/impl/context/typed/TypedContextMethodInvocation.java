package ar.com.dgarcia.javaspec.impl.context.typed;

import ar.com.dgarcia.javaspec.api.TestContext;
import ar.com.dgarcia.javaspec.impl.exceptions.SpecException;

import java.lang.reflect.Method;
import java.util.function.Supplier;

/**
 * This types represents a method invocation with actual arguments
 * Created by kfgodel on 21/07/14.
 */
public class TypedContextMethodInvocation {

    public static final String LET_PREFIX = "let";
    public static final String GET_PREFIX = "get";

    private Method method;
    private Object[] args;

    public static TypedContextMethodInvocation create(Method method, Object[] args) {
        TypedContextMethodInvocation invocation = new TypedContextMethodInvocation();
        invocation.method = method;
        invocation.args = args;
        return invocation;
    }

    /**
     * Inficates if this invocation can be executed directly over a TestContext instance
     * @return false if it's not part of the TestContext interface
     */
    public boolean canBeHandledByTestContext() {
        return this.method.getDeclaringClass().equals(TestContext.class);
    }

    /**
     * Executes this invocation on the given test context instance
     * @param context The context to be used as receiver
     * @return The invocation result
     */
    public Object invokeOn(TestContext context) {
        try {
            return this.method.invoke(context, this.args);
        } catch (Exception e) {
            throw new SpecException("Invocation on proxied context failed",e);
        }
    }

    /**
     * Returns the first argument used as variable definition
     * @return The supplier argument of the invocation
     */
    public Supplier<Object> getVariableDefinitionArgument() {
        if(this.args == null || this.args.length != 1){
            return null;
        }
        Object firstArgument = this.args[0];
        Supplier<Object> variableDefinition = null;
        try {
            variableDefinition = (Supplier<Object>) firstArgument;
        } catch (ClassCastException e) {
            throw new SpecException("Invocation should have only a supplier argument",e);
        }
        return variableDefinition;
    }

    /**
     * Returns the name of the variable implied in this method name
     * @return The method name without let or get prefixes
     */
    public String getVariableName() {
        String methodName = this.method.getName();
        if(methodName.startsWith(LET_PREFIX)){
            return methodName.substring(LET_PREFIX.length());
        }
        if(methodName.startsWith(GET_PREFIX)){
            return methodName.substring(GET_PREFIX.length());
        }
        return methodName;
    }
}

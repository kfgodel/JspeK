package ar.com.dgarcia.javaspec.impl.context.typed;

import ar.com.dgarcia.javaspec.api.TestContext;
import ar.com.dgarcia.javaspec.impl.exceptions.SpecException;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

/**
 * This type validates a TypedContext interface to avoid wrong interface definition
 * Created by kfgodel on 22/07/14.
 */
public class TypedContextValidator {

    private Map<String, Method> getters;
    private Map<String, Method> letters;

    private Class<? extends TestContext> validable;

    /**
     * Throws a specException if validation error is found on the interface
     */
    public void validate() throws SpecException {
        parseGettersAndLetters();
        findUnbalancedPairs();
        findMismatchedTypePair();
    }

    /**
     * Searches for a pair of method with mismatching type definition
     */
    private void findMismatchedTypePair() {
        Set<String> allVariables = letters.keySet();
        for (String variable : allVariables) {
            Type letType = this.getLetTypeFor(variable);
            Type getType = this.getGetTypeFor(variable);
            if(this.isAMismatch(letType, getType)){
                throw new SpecException("Variable ["+variable+"] has mismatching type definitions in get ["+getType.getTypeName()+"] and let ["+letType.getTypeName()+"] operations");
            }
        }
    }

    private Type getLetTypeFor(String variable) {
        Method letMethod = letters.get(variable);
        Type supplierType = letMethod.getGenericParameterTypes()[0];
        if(!(supplierType instanceof ParameterizedType)){
            throw new SpecException("Argument of let operation ["+variable+"] is not a parameterized Supplier? " + supplierType);
        }
        ParameterizedType parameterizedSupplier = (ParameterizedType) supplierType;
        Type[] supplierArgs = parameterizedSupplier.getActualTypeArguments();
        Type letType = supplierArgs[0];
        return letType;
    }

    private Type getGetTypeFor(String variable) {
        Method getterMethod = getters.get(variable);
        Type getType = getterMethod.getGenericReturnType();
        return getType;
    }

    private boolean isAMismatch(Type letType, Type getType) {
        if(!(letType instanceof Class) || !(getType instanceof Class)){
            //We can't validate generic types
            return false;
        }
        boolean letIsUnassignableToGet = !((Class<?>) getType).isAssignableFrom((Class<?>) letType);
        return letIsUnassignableToGet;
    }

    /**
     * Searches for lets without gets, or gets without lets
     */
    private void findUnbalancedPairs() {
        Set<String> definedLetters = letters.keySet();
        for (String letVariable : definedLetters) {
            if(!getters.containsKey(letVariable)){
                throw new SpecException("Variable ["+letVariable+"] is missing get operation in ["+validable.getSimpleName()+"]");
            }
        }
        Set<String> definedGetters = getters.keySet();
        for (String getVariable : definedGetters) {
            if(!letters.containsKey(getVariable)){
                throw new SpecException("Variable ["+getVariable+"] is missing let operation in ["+validable.getSimpleName()+"]");
            }
        }
    }

    /**
     * Groups methods based on its signature, grouping in get an let operations
     */
    private void parseGettersAndLetters() {
        LinkedList<Class<?>> pendingTypes = new LinkedList<>();
        pendingTypes.push(validable);
        while (!pendingTypes.isEmpty()){
            Class<?> currentType = pendingTypes.pop();
            if(currentType.equals(Object.class) || currentType.equals(TestContext.class)){
                //We cut the search
                continue;
            }
            Method[] typeMethods = currentType.getDeclaredMethods();
            for (Method typeMethod : typeMethods) {
                String variableName = TypedContextMethodInvocation.extractVariableNameFrom(typeMethod);
                if(this.seemsLikeLetter(typeMethod)){
                    if(letters.containsKey(variableName)){
                        throw new SpecException("Let operation for variable ["+variableName+"] is duplicated");
                    }
                    letters.put(variableName, typeMethod);
                } else if (this.seemsLikeGetter(typeMethod)){
                    if(getters.containsKey(variableName)){
                        throw new SpecException("Get operation for variable ["+variableName+"] is duplicated");
                    }
                    getters.put(variableName, typeMethod);
                }else{
                    throw new SpecException("Method ["+typeMethod.getName()+"] declared in ["+currentType.getSimpleName()+"] does not conform to get or let operation signatures [no arg | void, Supplier]");
                }
            }
            Class<?>[] superInterfaces = currentType.getInterfaces();
            for (Class<?> superInterface : superInterfaces) {
                pendingTypes.add(superInterface);
            }
        }
    }

    private boolean seemsLikeGetter(Method typeMethod) {
        boolean doesNotReturnsVoid = !returnsVoid(typeMethod);;
        boolean hasNoArgs = typeMethod.getParameterCount() == 0;
        return doesNotReturnsVoid && hasNoArgs;
    }

    private boolean seemsLikeLetter(Method typeMethod) {
        boolean returnsVoid = returnsVoid(typeMethod);
        Class<?>[] argTypes = typeMethod.getParameterTypes();
        boolean hasOnSupplierArg = argTypes.length == 1 && argTypes[0].equals(Supplier.class);
        return returnsVoid && hasOnSupplierArg;
    }

    private boolean returnsVoid(Method typeMethod) {
        return typeMethod.getReturnType().equals(Void.TYPE);
    }

    public static TypedContextValidator create(Class<? extends TestContext> validableInterface) {
        TypedContextValidator validator = new TypedContextValidator();
        validator.validable = validableInterface;
        validator.getters = new HashMap<>();
        validator.letters = new HashMap<>();
        return validator;
    }
}

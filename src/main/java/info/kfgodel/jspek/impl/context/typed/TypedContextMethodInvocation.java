package info.kfgodel.jspek.impl.context.typed;

import info.kfgodel.jspek.api.contexts.TestContext;
import info.kfgodel.jspek.api.exceptions.SpecException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.function.Supplier;

/**
 * This types represents a method invocation with actual arguments
 * Created by kfgodel on 21/07/14.
 */
public class TypedContextMethodInvocation {
  public static Logger LOG = LoggerFactory.getLogger(TypedContextMethodInvocation.class);

  public static final String LET_PREFIX = "let";
  public static final String GET_PREFIX = "get";

  private Method method;
  private Object[] args;

  public static TypedContextMethodInvocation create(Method method, Object[] args) {
    TypedContextMethodInvocation invocation = new TypedContextMethodInvocation();
    invocation.method = method;
    invocation.args = args; //NOSONAR squid:S2384 Nobody will modify args
    return invocation;
  }

  /**
   * Inficates if this invocation can be executed directly over a TestContext instance
   *
   * @param testContext The context in which this invocation is verified
   * @return false if it's not part of the TestContext interface
   */
  public boolean canBeHandledByTestContext(TestContext testContext) {
    Class<?> receiverType = this.method.getDeclaringClass();
    return receiverType.isInstance(testContext);
  }


  /**
   * Executes this invocation on the given test context instance
   *
   * @param context The context to be used as receiver
   * @return The invocation result
   */
  public Object invokeOn(TestContext context) {
    try {
      return this.method.invoke(context, this.args);
    } catch (SpecException e) {
      throw e;
    } catch (InvocationTargetException e) {
      LOG.trace("Failed invocation. Analyzing cause", e);
      Throwable cause = e.getCause();
      if (cause instanceof SpecException) {
        throw (SpecException) cause;
      }
      throw new SpecException("Invocation on proxied context failed: " + cause.getMessage(), cause);
    } catch (Exception e) { // NOSONAR squid:S2221 we are doing reflection and its the catch all other errors case
      throw new SpecException("Unexpected error on proxied context invocation: " + e.getMessage(), e);
    }
  }

  /**
   * Returns the first argument used as variable definition
   *
   * @return The supplier argument of the invocation
   */
  @SuppressWarnings("unchecked")
  // There's no compile time way yo verify exact generic argument on runtime from an Object argument
  public Supplier<Object> getVariableDefinitionArgument() {
    if (this.args == null || this.args.length != 1) {
      return null;
    }
    Object firstArgument = this.args[0];
    try {
      return (Supplier<Object>) firstArgument;
    } catch (ClassCastException e) {
      throw new SpecException("Invocation should have only a supplier argument", e);
    }
  }

  /**
   * Returns the name of the variable implied in this method name
   *
   * @return The method name without let or get prefixes
   */
  public String getVariableName() {
    return extractVariableNameFrom(this.method);
  }

  public static String extractVariableNameFrom(Member method) {
    String variableName = method.getName();
    if (variableName.startsWith(LET_PREFIX) && variableName.length() > LET_PREFIX.length()) {
      variableName = variableName.substring(LET_PREFIX.length());
    } else if (variableName.startsWith(GET_PREFIX) && variableName.length() > GET_PREFIX.length()) {
      variableName = variableName.substring(GET_PREFIX.length());
    } else {
      // Use it as it is
    }
    char firstLetter = variableName.charAt(0);
    if (Character.isUpperCase(firstLetter)) {
      variableName = Character.toLowerCase(firstLetter) + variableName.substring(1);
    }
    return variableName;
  }
}

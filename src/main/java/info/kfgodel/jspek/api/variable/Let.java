package info.kfgodel.jspek.api.variable;

import info.kfgodel.jspek.api.contexts.TestContext;

import java.util.function.Supplier;

/**
 * This class allows variable definitions in tests suites that are lazily accessed and can be redefined in subcontexts
 * @param <T> Type of values produced by this operator
 * Created by nrainhart on 15/03/19.
 */
public class Let<T> {

  private String variableName;
  private Supplier<TestContext> context;

  /**
   * Creates a new instance
   * @param variableName The name of the variable to be assumed by this operator
   * @param context The context in which the assignments are allowed
   * @param <T> The type of values
   * @return A new instance
   */
  public static <T> Let<T> create(String variableName, Supplier<TestContext> context) {
    Let<T> let = new Let<>();
    let.variableName = variableName;
    let.context = context;
    return let;
  }

  /**
   * Defines the value in the current context, which may redefine previous value of broader context,
   * or be redefined by a subcontext.<br> An exception is thrown if a variable is tried to be defined twice
   * in same context
   *
   * @param definition A value supplier that can be used to lazily define the initial value of the variable
   * @return The newly defined operator
   */
  @SuppressWarnings("unchecked")
  // We hack the return type here given the supplier type (we are changing this type on runtime, which is not
  // possible on compile time)
  public <U extends T> Let<U> let(Supplier<T> definition) {
    context().let(variableName(), definition);
    return (Let<U>) this;
  }

  /**
   * Gets the value defined in the current context or parent context.<br>
   * The value of the variable is lazily defined the first time accessed. If there's no previous
   * definition of the variable, then an exception will be thrown.
   *
   * @return The value of the variable
   */
  public T get() {
    return context().get(variableName());
  }

  private String variableName() {
    return variableName;
  }

  private TestContext context() {
    return context.get();
  }

}

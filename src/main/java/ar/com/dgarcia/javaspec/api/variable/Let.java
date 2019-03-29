package ar.com.dgarcia.javaspec.api.variable;

import ar.com.dgarcia.javaspec.api.contexts.TestContext;

import java.util.function.Supplier;

public class Let<T> {

  private String variableName;
  private Supplier<TestContext> context;

  public static <T> Let<T> create(String variableName, Supplier<TestContext> context) {
    Let<T> let = new Let<>();
    let.variableName = variableName;
    let.context = context;
    return let;
  }

  /**
   * Defines the value in the current context, which may redefine previous value of broader context,
   * or be redefined by a subcontext.<br> An exception is thrown if a variable is tried to be defined twice in same context
   *
   * @param definition A value supplier that can be used to lazily define the initial value of the variable
   */
  public void set(Supplier<T> definition) {
    context().let(variableName(), definition);
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

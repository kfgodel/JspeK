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

  public void set(Supplier<T> definition) {
    context().let(variableName(), definition);
  }

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

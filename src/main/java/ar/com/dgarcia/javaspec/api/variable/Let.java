package ar.com.dgarcia.javaspec.api.variable;

import ar.com.dgarcia.javaspec.api.contexts.TestContext;

import java.util.function.Supplier;

public class Let<T> {

  private String name;
  private Supplier<TestContext> context;

  public static <T> Let<T> create(String name, Supplier<TestContext> context) {
    Let<T> let = new Let<>();
    let.name = name;
    let.context = context;
    return let;
  }

  public void set(Supplier<T> definition) {
    context().let(name(), definition);
  }

  public T get() {
    return context().get(name());
  }

  private String name() {
    return name;
  }

  private TestContext context() {
    return context.get();
  }

}

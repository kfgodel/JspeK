package ar.com.dgarcia.javaspec.examples;

import ar.com.dgarcia.javaspec.api.contexts.TestContext;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

/**
 * Date: 08/09/18 - 22:28
 */
public interface ExampleTestContext extends TestContext {

  String word();
  void word(Supplier<String> definition);

  List<String> elements();
  void elements(Supplier<List<String>> definition);

  Collection<String> collection();
  void collection(Supplier<Collection<String>> definition);

  Runnable code();
  void code(Supplier<Runnable> definition);

}

package info.kfgodel.jspek.examples;

import info.kfgodel.jspek.api.contexts.TestContext;

import java.util.function.Supplier;

/**
 * Date: 15/10/19 - 22:28
 */
public interface ReadmeExampleTestContext extends TestContext {

  Integer age();
  void age(Supplier<Integer> definition);

  String name();
  void name(Supplier<String> definition);

  Boolean canBuyAlcohol();
  void canBuyAlcohol(Supplier<Boolean> definition);

}

package ar.com.dgarcia.javaspec;

import ar.com.dgarcia.javaspec.api.contexts.TestContext;

import java.util.List;
import java.util.function.Supplier;

/**
 * This type represents the test context that is used for given-when-then test
 * Created by kfgodel on 09/03/16.
 */
public interface GiveWhenThenTestContext extends TestContext {

  List<String> list();
  void list(Supplier<List<String>> definition);

}

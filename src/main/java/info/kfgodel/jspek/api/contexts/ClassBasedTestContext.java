package info.kfgodel.jspek.api.contexts;

import info.kfgodel.jspek.api.exceptions.SpecException;

import java.util.function.Supplier;

/**
 * This type represents a test context where tests are centered on a class and
 * the test subject is an instance of that class
 *
 * Created by kfgodel on 08/03/16.
 */
public interface ClassBasedTestContext<T> extends TestContext {

  /**
   * Defines or overrides the described class in the current context
   * @param definition The class supplier definition
   */
  void describedClass(Supplier<Class<T>> definition);

  /**
   * The class under test that was declared with describe(Class,lmabda)
   *
   * @return The class under test or throws an excception if the class was not defined
   */
  Class<T> describedClass() throws SpecException;

  /**
   * @return An instance of the class under test, created by default with the empty constructor.<br>
   *   A new instance is created per test
   */
  T subject();

  /**
   * Defines or overrides the definition of a subject creation
   * @param definition The supplier to get a subject instance
   */
  void subject(Supplier<T> definition);
}

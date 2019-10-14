package info.kfgodel.jspek.api.contexts;


import info.kfgodel.jspek.api.exceptions.SpecException;

import java.util.function.Supplier;

/**
 * This type represents the context specific to a test that can be manipulated to represent different scenarios
 * Created by kfgodel on 18/07/14.
 */
public interface TestContext {
  /**
   * Defines the value to a named variable in the current context, which may redefine previous value of broader context,
   * or be redefined by a subcontext.<br> An exception is thrown if a variable is tried to be defined twice in
   * same context
   *
   * @param variableName    The name to identify the variable
   * @param valueDefinition A value supplier that can be used to lazily define the initial value of the variable
   * @throws SpecException If the variable has an already defined value
   */
  void let(String variableName, Supplier<?> valueDefinition) throws SpecException;

  /**
   * Gets the value of the named variable defined in the current context or parent context.<br>
   * The value of the variable is lazily defined the first time accessed. If there's no previous
   * definition of the variable, then an exception will be thrown.
   *
   * @param variableName The variable name which will be accessed (and maybe initialized)
   * @param <T>          Type of expected return to avoid casting
   * @return The value of the variable
   */
  <T> T get(String variableName);

  /**
   * Indicates whether this context has a definition for the given named variable or not.<br>
   * Definition may be inherited from parent context.<br>
   * Variable definition is independepent from variable value. The definition must be always be available for a
   * value to be able to be present
   *
   * @param variableName Name of the variable to check for
   * @return True if a value can be obtained calling get()
   */
  boolean hasDefinitionFor(String variableName);

  /**
   * @return The code defined as the setup part of a single test.<br>
   */
  Runnable setupCode();

  /**
   * Defines or overrides the code for the setup part of a test indicated with #given().<br>
   *
   * @param definition The code to define the initial context state for the test
   */
  void setupCode(Supplier<Runnable> definition);

  /**
   * @return The code defined as the exercise part of a single test
   */
  Runnable exerciseCode();

  /**
   * Defines or overrides the code for the exercise part of a test indicated with #when()
   *
   * @param definition The code to define the context modification, the tested logic
   */
  void exerciseCode(Supplier<Runnable> definition);

  /**
   * @return The code defined as the assertion part of a single test
   */
  Runnable assertionCode();

  /**
   * Defines or overrides the code for the assertion part of a test indicated with #then()
   *
   * @param definition The code to define the assertions over the context to verify the expected final state
   */
  void assertionCode(Supplier<Runnable> definition);


}

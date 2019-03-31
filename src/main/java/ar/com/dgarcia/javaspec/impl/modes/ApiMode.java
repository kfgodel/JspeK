package ar.com.dgarcia.javaspec.impl.modes;

import ar.com.dgarcia.javaspec.api.JavaSpecApi;
import ar.com.dgarcia.javaspec.api.contexts.TestContext;
import ar.com.dgarcia.javaspec.impl.model.SpecTree;

/**
 * This interface defines an internal protocol for handling the diferent api modes
 * Date: 30/03/19 - 22:29
 */
public interface ApiMode<T extends TestContext> extends JavaSpecApi<T> {
  /**
   * Changes the current mode to the specified type.<br>
   *   It fails if this mode can change to the requested mode
   * @return The new mode for the api
   */
  ApiMode<T> changeToDefinition();

  /**
   * Changes the current mode to running. It fails if this mode cannot make the change
   * @return The new mode
   */
  ApiMode<T> changeToRunning();

  /**
   * @return The tree on its current mode state
   */
  SpecTree getTree();
}

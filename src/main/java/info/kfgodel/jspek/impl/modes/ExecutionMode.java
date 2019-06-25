package info.kfgodel.jspek.impl.modes;

import info.kfgodel.jspek.api.JavaSpecApi;
import info.kfgodel.jspek.api.contexts.TestContext;

/**
 * This type defines the contract that spec implementations can use during a specific stage of tests' execution
 */
public interface ExecutionMode<T extends TestContext> extends JavaSpecApi<T> {
}

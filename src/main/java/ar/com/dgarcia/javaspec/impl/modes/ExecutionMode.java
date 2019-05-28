package ar.com.dgarcia.javaspec.impl.modes;

import ar.com.dgarcia.javaspec.api.JavaSpecApi;
import ar.com.dgarcia.javaspec.api.contexts.TestContext;

/**
 * This type defines the contract that spec implementations can use during a specific stage of tests' execution
 */
public interface ExecutionMode<T extends TestContext> extends JavaSpecApi<T> {
}

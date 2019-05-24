package ar.com.dgarcia.javaspec.impl.modes;

import ar.com.dgarcia.javaspec.api.JavaSpecApi;
import ar.com.dgarcia.javaspec.api.contexts.TestContext;

public interface ExecutionMode<T extends TestContext> extends JavaSpecApi<T> {
}

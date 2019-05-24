package api

import api.variable.UninitializedLet
import ar.com.dgarcia.javaspec.api.JavaSpec
import ar.com.dgarcia.javaspec.api.contexts.TestContext

abstract class KotlinSpec : JavaSpec<TestContext>() {
  override fun getContextTypeFromSubclassDeclaration(): Class<TestContext> {
    return TestContext::class.java
  }

  fun let(variableName: String): UninitializedLet {
    return UninitializedLet(variableName) { context() }
  }
}
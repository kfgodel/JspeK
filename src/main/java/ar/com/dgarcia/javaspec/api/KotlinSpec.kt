package ar.com.dgarcia.javaspec.api

import ar.com.dgarcia.javaspec.api.contexts.TestContext

abstract class KotlinSpec : JavaSpec<TestContext>() {
  override fun getContextTypeFromSubclassDeclaration(): Class<TestContext> {
    return TestContext::class.java
  }
}
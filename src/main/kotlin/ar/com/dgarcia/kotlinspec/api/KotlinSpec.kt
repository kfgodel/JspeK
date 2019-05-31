package ar.com.dgarcia.kotlinspec.api

import ar.com.dgarcia.javaspec.api.JavaSpec
import ar.com.dgarcia.javaspec.api.contexts.TestContext
import ar.com.dgarcia.kotlinspec.api.variable.Let
import ar.com.dgarcia.kotlinspec.api.variable.UninitializedLet
import kotlin.reflect.KProperty


abstract class KotlinSpec : JavaSpec<TestContext>() {
  override fun getContextTypeFromSubclassDeclaration(): Class<TestContext> {
    return TestContext::class.java
  }

  fun <T> let() = LetDelegate<T> { context() }

  class LetDelegate<T>(private val context: () -> TestContext) {
    operator fun getValue(thisRef: Nothing?, property: KProperty<*>): Let<T> {
      return UninitializedLet(property.name, context)
    }
  }
}

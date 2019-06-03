package ar.com.dgarcia.kotlinspec.api

import ar.com.dgarcia.javaspec.api.JavaSpec
import ar.com.dgarcia.javaspec.api.contexts.TestContext
import ar.com.dgarcia.kotlinspec.api.variable.TestVariable
import kotlin.reflect.KProperty

/**
 * This class is the extension point to add testing functionality exclusive to Kotlin Specs.<br>
 * Created by nrainhart on 24/05/19.
 */
abstract class KotlinSpec : JavaSpec<TestContext>() {
  override fun getContextTypeFromSubclassDeclaration(): Class<TestContext> {
    return TestContext::class.java
  }

  fun <T> let() = LetDelegate<T> { context() }

  fun <T> let(definition: () -> T) = LetDelegate(definition) { context() }

  class LetDelegate<T>(private val initialValue: (() -> T)? = null, private val context: () -> TestContext) {
    operator fun getValue(thisRef: Nothing?, property: KProperty<*>): TestVariable<T> {
        val variableName = property.name
        val returnedVariable = TestVariable<T>(variableName, context)
        if (initialValue != null) {
            returnedVariable.set(initialValue)
        }
        return returnedVariable
    }
  }
}

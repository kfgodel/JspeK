package info.kfgodel.jspek.api

import info.kfgodel.jspek.api.contexts.TestContext
import info.kfgodel.jspek.api.variable.TestVariable
import kotlin.reflect.KProperty

/**
 * This class is the extension point to add testing functionality exclusive to Kotlin Specs.<br>
 * Created by nrainhart on 24/05/19.
 */
abstract class KotlinSpec : JavaSpec<TestContext>() {
  override fun getContextTypeFromSubclassDeclaration(): Class<TestContext> {
    return TestContext::class.java
  }

  fun <T> let() = UninitializedLetDelegate<T> { context() }

  fun <T> let(definition: () -> T) = InitializedLetDelegate(definition) { context() }

  class UninitializedLetDelegate<T>(private val context: () -> TestContext) {
    operator fun getValue(thisRef: Nothing?, property: KProperty<*>): TestVariable<T> {
      return TestVariable(property.name, context)
    }
  }

  class InitializedLetDelegate<T>(private val initialValue: () -> T, private val context: () -> TestContext) {
    operator fun getValue(thisRef: Nothing?, property: KProperty<*>): TestVariable<T> {
      val createdVariable = TestVariable<T>(property.name, context)
      if (!context().hasDefinitionFor(createdVariable.variableName)) {
        // We define one
        createdVariable.set(initialValue)
      }
      return createdVariable
    }

  }
}

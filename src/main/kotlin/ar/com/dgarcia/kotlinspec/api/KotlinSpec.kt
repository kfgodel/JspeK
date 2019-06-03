package ar.com.dgarcia.kotlinspec.api

import ar.com.dgarcia.javaspec.api.JavaSpec
import ar.com.dgarcia.javaspec.api.contexts.TestContext
import ar.com.dgarcia.javaspec.api.exceptions.SpecException
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
        try {
            createdVariable.set(initialValue)
        } catch (e: SpecException) {
            if(e.message!!.contains("cannot be re-defined once assigned.")){
                // It's already assigned from previous call
                return createdVariable
            }else{
                throw e
            }
        }
        return createdVariable
    }
  }
}

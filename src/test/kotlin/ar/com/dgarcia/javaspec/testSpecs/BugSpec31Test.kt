package ar.com.dgarcia.javaspec.testSpecs

import ar.com.dgarcia.javaspec.api.JavaSpecRunner
import ar.com.dgarcia.kotlinspec.api.KotlinSpec
import org.assertj.core.api.Assertions.assertThat
import org.junit.runner.RunWith

/**
 * This class verifies the correct behavior for bug SPEC-31
 * Date: 02/06/19 - 20:05
 */
@RunWith(JavaSpecRunner::class)
class BugSpec31Test : KotlinSpec() {
    override fun define() {
        describe("a predefined variable") {
            val variable by let { 23 }

            it("is defined the first time it's accessed") {
                assertThat(variable.get()).isEqualTo(23)
            }

            it("can be accessed twice") {
                val firstValue = variable.get()
                val secondValue = variable.get()
                assertThat(firstValue).isEqualTo(23)
                assertThat(secondValue).isEqualTo(23)
            }
        }
    }
}
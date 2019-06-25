package info.kfgodel.jspek.testSpecs

import info.kfgodel.jspek.api.JavaSpecRunner
import info.kfgodel.jspek.api.KotlinSpec
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

            describe("inside a sub context") {
                variable.set { 24 }
                it("can be redefined") {
                    assertThat(variable.get()).isEqualTo(24)
                }

                describe("with nested context") {

                    it("can access the outermost definition") {
                        assertThat(variable.get()).isEqualTo(24)
                    }
                }
            }
            describe("even on a sibling sub context") {
                variable.set { 25 }
                it("can be redefined") {
                    assertThat(variable.get()).isEqualTo(25)
                }
            }
        }
    }
}
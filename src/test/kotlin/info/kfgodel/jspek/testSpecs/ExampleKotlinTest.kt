package info.kfgodel.jspek.testSpecs

import info.kfgodel.jspek.api.JavaSpecRunner
import info.kfgodel.jspek.api.KotlinSpec
import org.assertj.core.api.Assertions.assertThat
import org.junit.runner.RunWith

/**
 * Date: 10/06/19 - 20:08
 */
@RunWith(JavaSpecRunner::class)
class ExampleKotlinTest : KotlinSpec() {
    override fun define() {
        describe("a kotlin spec") {

            it("contains a test with an expectation") {
                assertThat(true).isEqualTo(true)
            }

            describe("when variables are needed") {

                val age by let { 23 }

                it("can set its value on declaration") {
                    assertThat(age()).isEqualTo(23)
                }

                val name by let<String>()
                name { "esther" }

                it("or after declaration") {
                    age { 22 }
                    assertThat(age()).isEqualTo(22)
                    assertThat(name()).isEqualTo("esther")
                }

                describe("when using nested contexts") {
                    name { "nested esther" }

                    it("can access outer variables") {
                        assertThat(age()).isEqualTo(23)
                    }

                    it("can re define the value in the subcontext") {
                        assertThat(name()).isEqualTo("nested esther")
                    }
                }
            }
        }
    }
}
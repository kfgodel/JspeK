package info.kfgodel.jspek.testSpecs

import info.kfgodel.jspek.api.JavaSpecRunner
import info.kfgodel.jspek.api.KotlinSpec
import org.assertj.core.api.Assertions.assertThat
import org.junit.runner.RunWith

/**
 * Example test to use on readme file for Kotlin
 * Date: 10/06/19 - 20:08
 */
@RunWith(JavaSpecRunner::class)
class ReadmeExampleKotlinTest : KotlinSpec() {
  override fun define() {
    describe("a kotlin spec") {

      it("contains a test with an expectation") {
        assertThat(true).isEqualTo(true)
      }

      describe("when test variables are needed inside a context") {

        val age by let { 23 }

        it("can access the value defined in its declaration") {
          assertThat(age()).isEqualTo(23)
        }

        it("can change its value inside a test") {
          age { 34 }
          assertThat(age()).isEqualTo(34)
        }

        describe("when redefined inside a sub-context") {
          age { 21 }

          it("access the redefined value") {
            assertThat(age()).isEqualTo(21)
          }
        }

        describe("when the value cannot be defined during declaration") {
          val name by let<String>()

          beforeEach {
            name { "lazy name" }
          }

          it("requires an explicit type for the variable") {
            assertThat(name()).isEqualTo("lazy name")
          }
        }

        describe("when dependent variables are needed") {
          val canBuyAlcohol by let { age() >= 21 }

          it("follows lexical scoping for variables to access each other") {
            assertThat(canBuyAlcohol()).isTrue()
          }
        }
      }
    }
  }
}
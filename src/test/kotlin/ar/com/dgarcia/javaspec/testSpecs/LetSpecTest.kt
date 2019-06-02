package ar.com.dgarcia.javaspec.testSpecs

import ar.com.dgarcia.javaspec.api.JavaSpecRunner
import ar.com.dgarcia.kotlinspec.api.KotlinSpec
import ar.com.dgarcia.kotlinspec.api.variable.TestVariable
import org.assertj.core.api.Assertions.assertThat
import org.junit.runner.RunWith

/**
 * This type tests usage of lets in Kotlin
 * Created by nrainhart on 11/05/19.
 */
@RunWith(JavaSpecRunner::class)
class LetSpecTest : KotlinSpec() {

  override fun define() {
    describe("lets") {

      describe("can be declared in suite contexts") {
        val foo: TestVariable<Int> by let()
        val predefinedValue by let { 3 }

        it("can have a value defined with its creation") {
          assertThat(predefinedValue.get()).isEqualTo(3)
        }

        describe("and its value can be set in contexts") {
          foo.set { 1 }

          it("can obtain that value") {
            assertThat(foo.get()).isEqualTo(1)
          }

          describe("when redefining its value in a sub-context") {
            foo.set { 2 }

            it("changes the original value") {
              assertThat(foo.get()).isEqualTo(2)
            }
          }

        }

        it("and its value can also be set inside a test") {
          foo.set { 3 }

          assertThat(foo.get()).isEqualTo(3)
        }
      }

      describe("one definition can use others") {

        val sum: TestVariable<Int> by let()
        val value: TestVariable<Int> by let()

        sum.set { 2 + value.get() }

        it("allowing to change parts of the test context") {
          value.set { 2 }

          assertThat(sum.get()).isEqualTo(4)
        }

        describe("or nesting scenarios") {
          value.set { 1 }

          it("with cleanly defined context") { assertThat(sum.get()).isEqualTo(3) }
        }
      }

    }

  }

}
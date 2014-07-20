package ar.com.dgarcia.javaspec;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This type verifies usage and behavior of 'let' operator in javaSpec
 * Created by kfgodel on 18/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class LetOperatorTest extends JavaSpec{
    @Override
    public void define() {
        describe("let/get operations", ()->{

            it("are lazy initialized variables that can be used to define the context of a test", ()->{
                context().let("foo", () -> 3);

                assertThat(context().<Integer>get("foo")).isEqualTo(3);
            });

            describe("can be used in the context of suite", ()->{

                context().let("foo", ()-> 1);

                beforeEach(()->{
                    //Or in setup code
                    context().let("foo", ()-> 2);
                });

                it("setup will have precedence over suite definition", ()->{
                    assertThat(context().<Integer>get("foo")).isEqualTo(2);
                });

                it("it definition has precedence over the other two", ()->{
                    context().let("foo", ()-> 3);
                    assertThat(context().<Integer>get("foo")).isEqualTo(3);
                });

            });

            describe("can have dependency over other definitions", ()->{
                context().let("sum", ()-> 2 + context().<Integer>get("value"));

                it("allowing to define specifics for each test", ()->{
                    context().let("value", ()-> 2);

                    assertThat(context().<Integer>get("sum")).isEqualTo(4);
                });

                describe("or nesting scenarios", ()->{
                    context().let("value", ()-> 1);

                    it("with fixed context", ()->{
                        assertThat(context().<Integer>get("sum")).isEqualTo(3);
                    });
                });

                it("once defined, a variable value remains the same", ()->{
                    context().let("value", ()-> 2);

                    assertThat(context().<Integer>get("sum")).isEqualTo(4);

                    context().let("value", ()-> 3);

                    assertThat(context().<Integer>get("sum")).isEqualTo(4);
                });
            });

        });
    }

}

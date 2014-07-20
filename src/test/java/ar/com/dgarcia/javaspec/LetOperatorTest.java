package ar.com.dgarcia.javaspec;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.dgarcia.javaspec.impl.exceptions.SpecException;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

/**
 * This type verifies usage and behavior of 'let' operator in javaSpec
 * Created by kfgodel on 18/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class LetOperatorTest extends JavaSpec{
    @Override
    public void define() {
        describe("let/get operations", ()->{

            it("are lazy named variables that can be used to define the context of a test", ()->{
                context().let("foo", () -> 3);

                assertThat(context().<Integer>get("foo")).isEqualTo(3);
            });

            describe("can be used in suite contexts", ()->{

                context().let("foo", ()-> 1);
                context().let("bar", ()-> 1);

                it("value is defined lazily the first time they are accessed", ()->{
                    assertThat(context().<Integer>get("foo")).isEqualTo(context().get("bar"));
                });

                describe("can be redefined in sub-contexts", ()->{
                    context().let("bar", ()-> 2);

                    it("value is evaluated for each test independently", ()->{
                        assertThat(context().<Integer>get("foo")).isNotEqualTo(context().get("bar"));
                    });
                });
            });

            describe("definitions are prioritized", ()->{

                context().let("foo", ()-> 1);

                beforeEach(()->{
                    //This will override context definition
                    context().let("foo", ()-> 2);
                });

                it("setup definition will have precedence over suite definition", ()->{
                    assertThat(context().<Integer>get("foo")).isEqualTo(2);
                });

                it("it definition has precedence over the other two", ()->{

                    context().let("foo", ()-> 3);

                    assertThat(context().<Integer>get("foo")).isEqualTo(3);
                });

            });

            describe("one definition can use others", ()->{

                context().let("sum", ()-> new Integer(2 + context().<Integer>get("value")) );

                it("allowing to change parts of the test context", ()->{
                    context().let("value", ()-> 2);

                    assertThat(context().<Integer>get("sum")).isEqualTo(4);
                });

                describe("or nesting scenarios", ()->{
                    context().let("value", ()-> 1);

                    it("with fixed context", ()->{
                        assertThat(context().<Integer>get("sum")).isEqualTo(3);
                    });
                });

                it("once defined, a variable value remains the same through test duration", ()->{
                    context().let("value", ()-> 2);

                    Integer firstTime = context().<Integer>get("sum");
                    assertThat(firstTime).isEqualTo(4);

                    Integer secondTime = context().<Integer>get("sum");
                    assertThat(secondTime).isSameAs(firstTime);
                });

                it("once defined, a variable value cannot be redefined", ()-> {
                    context().let("value", ()-> 1);
                    context().get("value");
                    try{
                        context().let("value", ()-> 2);
                        failBecauseExceptionWasNotThrown(SpecException.class);
                    }catch(SpecException e){
                        assertThat(e).hasMessage("Variable [value] cannot be re-defined once assigned. Current value: [1]");
                    }
                });
            });
        });
    }

}

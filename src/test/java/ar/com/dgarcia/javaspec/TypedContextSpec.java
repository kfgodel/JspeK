package ar.com.dgarcia.javaspec;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.dgarcia.javaspec.api.TestContext;
import org.junit.runner.RunWith;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This type tests usage of typed context
 * Created by kfgodel on 22/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class TypedContextSpec extends JavaSpec<TypedContextSpec.TestingContext> {

    public static interface TestingContext extends TestContext{

        //Foo declaration
        void letFoo(Supplier<Integer> definition);
        Integer getFoo();

        //bar declaration
        void bar(Supplier<Integer> definition);
        Integer bar();
    }

    @Override
    public void define() {

        describe("using typed contexts", ()->{

            describe("a variable can be defined with let prefix", ()->{
                context().letFoo(()-> 1);

                it("and accessed with get prefix", ()->{
                    assertThat(context().getFoo()).isEqualTo(1);
                });
            });


            describe("without prefixes", ()->{
                context().bar(()-> 2);

                it("accessed by variable name as method", ()->{
                    assertThat(context().bar()).isEqualTo(2);
                });
            });

            describe("using names", ()->{
                context().letFoo(()-> 3);

                it("prefixes are omitted", ()->{
                    assertThat(context().<Integer>get("foo")).isEqualTo(3);
                });

            });

        });

    }
}

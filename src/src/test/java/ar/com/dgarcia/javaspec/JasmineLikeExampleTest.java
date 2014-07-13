package ar.com.dgarcia.javaspec;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.dgarcia.javaspec.api.Variable;
import org.junit.runner.RunWith;

import java.lang.Boolean;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This class serves as an example of Java-Spec.<br>
 *     Based on http://jasmine.github.io/2.0/introduction.html
 * Created by kfgodel on 12/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class JasmineLikeExampleTest extends JavaSpec {

    private Variable<Integer> foo;
    private Variable<String> bar;

    public void define(){

        // introduction.js
        describe("A suite", ()-> {
            it("contains spec with an expectation", ()-> {
                assertThat(true).isEqualTo(true);
            });
        });


        // It’s Just Functions
        describe("A suite is just a lambda", ()-> {

            Variable<Boolean> a = Variable.create();

            it("and so is a spec", ()-> {
                a.set(true);

                assertThat(a.get()).isEqualTo(true);
            });
        });


        // Expectations
        describe("AssertJ assertions can be used in the spec", ()-> {

            it("and has a positive case", ()-> {
                assertThat(true).isEqualTo(true);
            });

            it("and can have a negative case", ()-> {
                assertThat(false).isNotEqualTo(true);
            });
        });


        // Setup and Teardown
        describe("A spec (with setup and tear-down)", ()-> {
            Variable<Integer> foo = Variable.create();

            beforeEach(()-> {
                foo.set(0);
                foo.storeSumWith(1);
            });

            afterEach(()-> {
                foo.set(0);
            });

            it("is just a lambda, so it can contain any code", ()-> {
                assertThat(foo.get()).isEqualTo(1);
            });

            it("can have more than one expectation", ()-> {
                assertThat(foo.get()).isEqualTo(1);
                assertThat(true).isEqualTo(true);
            });

        });


        // The this keyword
        describe("A spec", ()-> {
            beforeEach(()-> {
                this.foo.set(0);
            });

            it("can use the `this` to share state", ()-> {
                assertThat(foo.get()).isEqualTo(0);
                this.bar.set("test pollution?");
            });

            it("prevents test pollution by having an empty `this` created for the next spec", ()-> {
                assertThat(foo.get()).isEqualTo(0);
                assertThat(bar.get()).isNull();
            });
        });


        // Disabling Suites
        xdescribe("A disabled group spec", ()-> {

            it("disabled implicitly", ()-> {
                assertThat(foo.get()).isEqualTo(1);
            });

            xit("disabled explicitly", ()-> {
                assertThat(foo.get()).isEqualTo(2);
            });
        });


        // Pending Specs
        describe("Pending specs", ()-> {

            xit("can be declared 'xit'", ()-> {
                assertThat(true).isEqualTo(false);
            });

            it("can be declared with 'it' but without a lambda");

        });


        // Spies
        describe("has spies with mockito", ()->{

            it("use mockito as usuarl", ()->{
                List mockedList = mock(List.class);
                when(mockedList.get(0)).thenReturn("First");

                assertThat(mockedList.get(0)).isEqualTo("First");
            });

        });

    }

}

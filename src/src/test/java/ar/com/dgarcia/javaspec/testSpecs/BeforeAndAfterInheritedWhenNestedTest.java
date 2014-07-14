package ar.com.dgarcia.javaspec.testSpecs;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.dgarcia.javaspec.api.Variable;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class serves as input spec for parser test
 * Created by kfgodel on 12/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class BeforeAndAfterInheritedWhenNestedTest extends JavaSpec {
    @Override
    public void define() {
        Variable<Object> foo = Variable.create();

        beforeEach(()-> {
            foo.set(0);
        });

        it("test with 1 before/after set", ()-> {
            assertThat(foo.get()).isEqualTo(0);
        });

        describe("nested context inherits before/after definitions", ()->{

            beforeEach(()-> {
                foo.storeSumWith(1);
            });

            it("test with own and inherited before/after sets", ()-> {
                assertThat(foo.get()).isEqualTo(1);
                foo.set("a text");
            });

            afterEach(()-> {
                foo.storeSumWith(" added something");
            });

        });

        afterEach(()-> {
            foo.storeSumWith(" period .");
        });

    }
}

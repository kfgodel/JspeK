package ar.com.dgarcia.javaspec.testSpecs;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.dgarcia.javaspec.api.contexts.TestContext;
import ar.com.dgarcia.javaspec.api.variable.Variable;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class serves as input spec for parser test
 * Created by kfgodel on 12/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class AfterUsedInOneTestSpecTest extends JavaSpec<TestContext> {
    @Override
    public void define() {
        Variable<Object> foo = Variable.create();

        afterEach(()-> {
            foo.storeSumWith("appended text");
        });

        it("test with after", ()-> {
            assertThat(foo.get()).isNull();
            foo.set("a text");
        });

    }
}

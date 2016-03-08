package ar.com.dgarcia.javaspec.testSpecs;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.dgarcia.javaspec.api.contexts.TestContext;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class serves as input spec for parser test
 * Created by kfgodel on 19/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class VariableInSuitSpecTest extends JavaSpec<TestContext> {

    @Override
    public void define() {
        describe("a suite with a variable", ()->{
            context().let("foo", ()-> 2);

            it("can be accessed from its tests", ()->{
                assertThat(context().<Integer>get("foo")).isEqualTo(2);
            });
        });
    }
}

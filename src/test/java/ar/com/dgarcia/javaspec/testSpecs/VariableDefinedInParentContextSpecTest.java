package ar.com.dgarcia.javaspec.testSpecs;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.dgarcia.javaspec.api.contexts.TestContext;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class serves as input spec for parser test
 * Created by kfgodel on 20/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class VariableDefinedInParentContextSpecTest extends JavaSpec<TestContext> {
    @Override
    public void define() {

        context().let("foo", ()-> 2);

        describe("a suite inherits variables from its container", ()->{

            it("can be accessed from its tests", ()->{
                assertThat(context().<Integer>get("foo")).isEqualTo(2);
            });
        });
    }
}
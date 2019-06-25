package info.kfgodel.jspek.testSpecs;

import info.kfgodel.jspek.api.JavaSpec;
import info.kfgodel.jspek.api.JavaSpecRunner;
import info.kfgodel.jspek.api.contexts.TestContext;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class serves as input spec for parser test
 * Created by kfgodel on 13/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class OneTestInsideDisabledSpecTest extends JavaSpec<TestContext> {

    @Override
    public void define() {

        xdescribe("disabled suite", ()->{

            it("transitive disabled test", ()->{
                assertThat(true).isFalse();
            });

        });

    }
}

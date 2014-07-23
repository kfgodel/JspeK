package ar.com.dgarcia.javaspec.testSpecs;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.dgarcia.javaspec.api.TestContext;
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

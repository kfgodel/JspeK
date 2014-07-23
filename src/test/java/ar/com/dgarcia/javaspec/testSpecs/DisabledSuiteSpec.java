package ar.com.dgarcia.javaspec.testSpecs;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.dgarcia.javaspec.api.TestContext;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class serves as input spec for parser test
 * Created by kfgodel on 12/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class DisabledSuiteSpec extends JavaSpec<TestContext> {
    @Override
    public void define() {

        xdescribe("a disabled spec", ()->{

            it("ignored test", ()-> {
                assertThat(true).isEqualTo(false);
            });

        });

    }
}

package ar.com.dgarcia.javaspec.testSpecs;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.runner.RunWith;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;

/**
 * This class serves as input spec for parser test
 * Created by kfgodel on 12/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class DisabledSuiteSpec extends JavaSpec {
    @Override
    public void define() {

        xdescribe("a disabled spec", ()->{

            it("ignored test", ()-> {
                assertThat(true).isEqualTo(false);
            });

        });

    }
}

package ar.com.dgarcia.javaspec.testSpecs;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.runner.RunWith;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;

/**
 * This class serves as input spec for parser test
 * Created by kfgodel on 13/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class OneTestInsideDisabledSpecTest extends JavaSpec {

    @Override
    public void define() {

        xdescribe("disabled suite", ()->{

            it("transitive disabled test", ()->{
                assertThat(true).isFalse();
            });

        });

    }
}

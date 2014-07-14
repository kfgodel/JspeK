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
public class OneTestInsideDescribeSpec extends JavaSpec {

    @Override
    public void define() {
        describe("A suite", ()-> {
            it("contains spec with an expectation", ()-> {
                assertThat(true).isEqualTo(true);
            });
        });
    }
}
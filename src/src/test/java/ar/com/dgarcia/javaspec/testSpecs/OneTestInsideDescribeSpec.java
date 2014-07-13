package ar.com.dgarcia.javaspec.testSpecs;

import ar.com.dgarcia.javaspec.api.JavaSpec;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by kfgodel on 12/07/14.
 */
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

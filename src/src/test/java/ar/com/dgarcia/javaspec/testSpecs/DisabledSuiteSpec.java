package ar.com.dgarcia.javaspec.testSpecs;

import ar.com.dgarcia.javaspec.api.JavaSpec;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by kfgodel on 12/07/14.
 */
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

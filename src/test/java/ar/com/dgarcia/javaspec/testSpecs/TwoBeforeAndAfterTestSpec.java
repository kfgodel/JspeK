package ar.com.dgarcia.javaspec.testSpecs;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.runner.RunWith;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.dgarcia.javaspec.api.Variable;

/**
 * This class serves as input spec for parser test
 * Created by kfgodel on 12/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class TwoBeforeAndAfterTestSpec extends JavaSpec {
    @Override
    public void define() {
        Variable<Object> foo = Variable.create();

        beforeEach(()-> {
            foo.set(0);
        });

        beforeEach(()-> {
            foo.storeSumWith(1);
        });

        it("test with 2 before and 2 after", ()-> {
            assertThat(foo.get()).isEqualTo(1);
            foo.set("text");
        });

        afterEach(()-> {
            foo.storeSumWith(" added something");
        });

        afterEach(()-> {
            foo.storeSumWith(" period .");
        });
    }
}

package ar.com.dgarcia.javaspec.testSpecs;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.Variable;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by kfgodel on 12/07/14.
 */
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

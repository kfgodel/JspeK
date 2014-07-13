package ar.com.dgarcia.javaspec.testSpecs;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.Variable;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by kfgodel on 12/07/14.
 */
public class AfterUsedInOneTestSpec extends JavaSpec {
    @Override
    public void define() {
        Variable<Object> foo = Variable.create();

        afterEach(()-> {
            foo.storeSumWith("appended text");
        });

        it("test with after", ()-> {
            assertThat(foo.get()).isNull();
            foo.set("a text");
        });

    }
}

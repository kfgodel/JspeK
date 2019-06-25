package info.kfgodel.jspek.testSpecs;

import info.kfgodel.jspek.api.JavaSpec;
import info.kfgodel.jspek.api.JavaSpecRunner;
import info.kfgodel.jspek.api.contexts.TestContext;
import info.kfgodel.jspek.api.variable.Variable;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class serves as input spec for parser test
 * Created by kfgodel on 12/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class BeforeUsedInOneTestSpecTest extends JavaSpec<TestContext> {
    @Override
    public void define() {
        Variable<Integer> foo = Variable.create();

        beforeEach(()-> {
            foo.set(0);
        });

        it("test with before", ()-> {
            assertThat(foo.get()).isEqualTo(0);
        });

    }
}

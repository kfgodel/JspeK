package ar.com.dgarcia.javaspec.testSpecs;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class serves as input spec for parser test
 * Created by kfgodel on 12/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class TwoPendingTestSpec extends JavaSpec {
    @Override
    public void define() {
        xit("xit pending test", ()->{
            assertThat(true).isEqualTo(false);
        });

        it("non lambda pending test");
    }
}

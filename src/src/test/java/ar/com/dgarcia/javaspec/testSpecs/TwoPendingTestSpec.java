package ar.com.dgarcia.javaspec.testSpecs;

import ar.com.dgarcia.javaspec.api.JavaSpec;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by kfgodel on 12/07/14.
 */
public class TwoPendingTestSpec extends JavaSpec {
    @Override
    public void define() {
        xit("xit pending test", ()->{
            assertThat(true).isEqualTo(false);
        });

        it("non lambda pending test");
    }
}

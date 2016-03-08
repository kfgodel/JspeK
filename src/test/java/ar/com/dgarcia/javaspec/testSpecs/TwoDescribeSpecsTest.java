package ar.com.dgarcia.javaspec.testSpecs;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.dgarcia.javaspec.api.contexts.TestContext;
import org.junit.runner.RunWith;

/**
 * This class serves as input spec for parser test
 * Created by kfgodel on 12/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class TwoDescribeSpecsTest extends JavaSpec<TestContext> {
    @Override
    public void define() {
        describe("first group", ()->{
            it("test in first group", ()->{

            });
        });

        describe("second group", ()->{
            it("test in second group", ()->{

            });
        });
    }
}

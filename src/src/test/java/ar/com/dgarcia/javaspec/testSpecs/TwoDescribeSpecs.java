package ar.com.dgarcia.javaspec.testSpecs;

import ar.com.dgarcia.javaspec.api.JavaSpec;

/**
 * Created by kfgodel on 12/07/14.
 */
public class TwoDescribeSpecs extends JavaSpec {
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

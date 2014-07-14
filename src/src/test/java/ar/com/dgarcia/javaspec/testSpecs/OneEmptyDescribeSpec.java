package ar.com.dgarcia.javaspec.testSpecs;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import org.junit.runner.RunWith;

/**
 * This class serves as input spec for parser test
 * Created by kfgodel on 12/07/14.
 */
public class OneEmptyDescribeSpec extends JavaSpec {
    @Override
    public void define() {
        describe("empty describe", ()->{

        });
    }
}

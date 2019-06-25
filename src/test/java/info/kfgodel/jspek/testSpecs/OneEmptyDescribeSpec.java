package info.kfgodel.jspek.testSpecs;

import info.kfgodel.jspek.api.JavaSpec;
import info.kfgodel.jspek.api.contexts.TestContext;

/**
 * This class serves as input spec for parser test
 * Created by kfgodel on 12/07/14.
 */
public class OneEmptyDescribeSpec extends JavaSpec<TestContext> {
    @Override
    public void define() {
        describe("empty describe", ()->{

        });
    }
}

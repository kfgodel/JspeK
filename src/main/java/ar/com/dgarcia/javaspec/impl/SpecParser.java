package ar.com.dgarcia.javaspec.impl;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.impl.model.SpecTree;

/**
 * This type defines the parser that understands the definition of a JavaSpec subclass, and creates a model of the specs
 * Created by kfgodel on 12/07/14.
 */
public class SpecParser {

    public static SpecParser create() {
        SpecParser parser = new SpecParser();
        return parser;
    }

    public SpecTree parse(Class<? extends JavaSpec> specClass) {
        return null;
    }
}

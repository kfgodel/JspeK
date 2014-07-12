package ar.com.dgarcia.javaspec;

/**
 * This class is the extension point to add testing expressiveness with Java Specs
 * Created by kfgodel on 12/07/14.
 */
public class JavaSpec {

    public void define(){

    }

    public void beforeEach(Runnable aCodeBlock) {

    }

    public void afterEach(Runnable aCodeBlock) {

    }

    public void it(String testName, Runnable aTestCode){

    }

    public void it(String testName){

    }

    public void xit(String testName, Runnable aTestCode){

    }

    public void describe(String aGroupName, Runnable aGroupDefinition){

    }

    public void xdescribe(String aGroupName, Runnable aGroupDefinition){

    }


}

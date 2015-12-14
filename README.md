# *JAVA-SPEC* #

Define your Junit tests as Specs:  

```
#!java

@RunWith(JavaSpecRunner.class)
public class ExampleTest extends JavaSpec<TestContext>{

    @Override
    public void define() {
    	
        describe("A suite", ()-> {
            it("contains spec with an expectation", ()-> {
                assertThat(true).isEqualTo(true);
            });
        });
        
    }
}
```
Based on [Jasmine](http://jasmine.github.io/) for javascript and [RSpec](http://rspec.info/) for Ruby
##**[See more examples in the wiki](https://bitbucket.org/kfgodel/java-spec/wiki/Home)**


### Maven dependency ###

* Add this repository to your pom:  
```
#!xml
    <repository>
      <id>kfgodel_mosquito</id>
      <name>Repo Mosquito</name>
      <url>http://kfgodel.info:8081/nexus/content/groups/public/</url>
    </repository>
```

* Declare the dependency
```
#!xml

<dependency>
  <groupId>ar.com.dgarcia</groupId>
  <artifactId>java-spec</artifactId>
  <version>2.0</version>
  <scope>test</scope>
</dependency>
```

### Non maven

Download the binaries from the [download section](https://bitbucket.org/kfgodel/java-spec/downloads)

### Junit error
If you get this error:
```
java.lang.NoSuchMethodError: org.junit.runner.Description.createSuiteDescription(Ljava/lang/String;Ljava/io/Serializable;[Ljava/lang/annotation/Annotation;)Lorg/junit/runner/Description;
    at ar.com.dgarcia.javaspec.impl.junit.JunitTestTreeAdapter.recursiveAdaptToJunit(JunitTestTreeAdapter.java:48)
    at ar.com.dgarcia.javaspec.impl.junit.JunitTestTreeAdapter.adaptToJunit(JunitTestTreeAdapter.java:39)
    at ar.com.dgarcia.javaspec.impl.junit.JunitTestTreeAdapter.create(JunitTestTreeAdapter.java:27)
```
The problem is the junit version. You need at least version 4.11 that exposes the Description object that is needed for JavaSpec to describe the tests.


### Who do I talk to? ###

Please make sure to read the documentation first. If it's not there then I will be glad to help you.  
Any problem you find, suggestions or general questions address them to dario.garcia at 10pines.com
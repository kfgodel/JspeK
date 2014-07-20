# *JAVA-SPEC* #

Define your Junit tests as Specs:  

```
#!java

@RunWith(JavaSpecRunner.class)
public class ExampleTest extends JavaSpec {

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
Based on Jasmine for javascript: http://jasmine.github.io/  
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
  <version>1.1</version>
  <scope>test</scope>
</dependency>
```

### Non maven

Download the binaries from the [download section](https://bitbucket.org/kfgodel/java-spec/downloads)

### Who do I talk to? ###

Please make sure to read the documentation first. If it's not there then I will be glad to help you.  
Any problem you find, suggestions or general questions address them to dario.garcia at 10pines.com
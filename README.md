# This project was moved to [github](https://github.com/kfgodel/java-spec)


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
##**[See more examples in the wiki](https://github.com/kfgodel/java-spec/wiki)**
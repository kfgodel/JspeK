# *JspeK* #

Define your Junit tests as specs with typed variables (for Java and Kotlin):  

- Kotlin
```kotlin
@RunWith(JavaSpecRunner::class)
class ReadmeExampleKotlinTest : KotlinSpec() {
  override fun define() {
    describe("a kotlin spec") {

      it("contains a test with an expectation") {
        assertThat(true).isEqualTo(true)
      }

      describe("when test variables are needed inside a context") {

        val age by let { 23 }

        it("can access the value defined in its declaration") {
          assertThat(age()).isEqualTo(23)
        }

        it("can change its value inside a test") {
          age { 34 }
          assertThat(age()).isEqualTo(34)
        }

        describe("when redefined inside a sub-context") {
          age { 21 }

          it("access the redefined value") {
            assertThat(age()).isEqualTo(21)
          }
        }

        describe("when the value cannot be defined during declaration") {
          val name by let<String>()

          beforeEach {
            name { "lazy name" }
          }

          it("requires an explicit type for the variable") {
            assertThat(name()).isEqualTo("lazy name")
          }
        }

        describe("when dependent variables are needed") {
          val canBuyAlcohol by let { age() >= 21 }

          it("follows lexical scoping for variables to access each other") {
            assertThat(canBuyAlcohol()).isTrue()
          }
        }
      }
    }
  }
}
```

- Java (it's a little more verbose)
```java
@RunWith(JavaSpecRunner.class)
public class ReadmeExampleJavaTest extends JavaSpec<ReadmeExampleTestContext> {
  @Override
  public void define() {
    describe("a java spec", () -> {

      it("contains a test with an expectation",()->{
        assertThat(true).isEqualTo(true);
      });

      describe("when test variables are needed inside a context", () -> {
        test().age(()-> 23);

        it("can access the value defined in its declaration",()->{
          assertThat(test().age()).isEqualTo(23);
        });

        it("can change its value inside a test",()->{
          test().age(()-> 34);
          assertThat(test().age()).isEqualTo(34);
        });

        describe("when redefined inside a sub-context", () -> {
          test().age(()-> 21);

          it("accesses the redefined value",()->{
            assertThat(test().age()).isEqualTo(21);
          });
        });

        describe("when the value cannot be defined during declaration", () -> {
          beforeEach(()->{
            test().name(()-> "lazy name");
          });

          it("doesn't require special syntax for defining it later", ()->{
            assertThat(test().name()).isEqualTo("lazy name");
          });
        });

        describe("when dependent variables are needed", () -> {
          test().canBuyAlcohol(()-> test().age() >= 21);

          it("doesn't follow lexical scope", ()->{ // I.e: dependent variable can be declared before independent
            assertThat(test().canBuyAlcohol()).isTrue();
          });

        });

      });
    });
  }
}
```
And requires and interface to be defined for test variables:
```java
public interface ReadmeExampleTestContext extends TestContext {

  Integer age();
  void age(Supplier<Integer> definition);

  String name();
  void name(Supplier<String> definition);

  Boolean canBuyAlcohol();
  void canBuyAlcohol(Supplier<Boolean> definition);

}
```

This project is inspired on [Jasmine](http://jasmine.github.io/) and [RSpec](http://rspec.info/) for Ruby  
  
For more examples and an exteded API tour [check usage details in the wiki](https://github.com/kfgodel/JspeK/wiki)
However, be aware that it's not yet updated with the latest changes

# Using it
### Gradle dependency ###

```
testImplementation("info.kfgodel:jspek:1.0.0")
```
(Note that old versions were deprecated after adding Kotlin support)

### Maven dependency ###

* Declare the dependency
```xml
<dependency>
  <groupId>info.kfgodel</groupId>
  <artifactId>jspek</artifactId>
  <version>1.0.1</version>
  <scope>test</scope>
</dependency>
```

### Old Junit version
If you get this error:
```
java.lang.NoSuchMethodError: org.junit.runner.Description.createSuiteDescription(Ljava/lang/String;Ljava/io/Serializable;[Ljava/lang/annotation/Annotation;)Lorg/junit/runner/Description;
    at JunitTestTreeAdapter.recursiveAdaptToJunit(JunitTestTreeAdapter.java:48)
    at JunitTestTreeAdapter.adaptToJunit(JunitTestTreeAdapter.java:39)
    at JunitTestTreeAdapter.create(JunitTestTreeAdapter.java:27)
```
The problem is that you are using and old Junit version. You need at least version **4.11** that exposes the 
Description object which is needed for JavaSpec to describe the tests.  
This hasn't been tested with Junit 5.x

```
<dependency>
  <groupId>junit</groupId>
  <artifactId>junit</artifactId>
  <version>4.11</version>
  <scope>test</scope>
</dependency>
```


### Who do I talk to? ###

Please make sure to read the documentation first. If it's not there then I will be glad to help you.  
Any problem you find, suggestions or general questions address them to dario.garcia at 10pines.com  

Thanks to Nicolas Rainhart for his full Kotlin port and collaboration 

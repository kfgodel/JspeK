# *JAVA-SPEC* #

Define your Junit tests as typed Specs (for Java and Kotlin):  

- Java
```java
@RunWith(JavaSpecRunner.class)
public class UsingContextAliasTest extends JavaSpec<ExampleTestContext> {
  
  @Override
  public void define() {
    describe("a list", () -> {
      test().list(ArrayList::new);

      describe("when created", () -> {
        it("is empty", () -> {
          assertThat(test().list().isEmpty()).isTrue();
        });

        itThrows(IndexOutOfBoundsException.class, "if accessed to its first element", () -> {
          test().list().get(0);
        }, e -> {
          assertThat(e).hasMessage("Index: 0, Size: 0");
        });
      });

      describe("when an element is added", () -> {
        beforeEach(() -> {
          test().list().add("1");
        });
        it("returns the first list element when accessed", () -> {
          assertThat(test().list().get(0)).isEqualTo("1");
        });
        it("is not empty", () -> {
          assertThat(test().list().isEmpty()).isFalse();
        });
      });

    });
  }
}
```

Define your typed test variables with an interface
```java
public interface ExampleTestContext extends TestContext {

  List<String> list();
  void list(Supplier<List<String>> definition);

}
```

- Kotlin
```kotlin
@RunWith(JavaSpecRunner::class)
class MinimumKotlinTest : KotlinSpec() {
  override fun define() {
    describe("a kotlin spec") {

      it("contains a test with an expectation") {
        assertThat(true).isEqualTo(true)
      }

      describe("when variables are needed") {

        val age by let { 23 }

        it("can set its value on declaration") {
          assertThat(age()).isEqualTo(23)
        }

        val name by let<String>()
        name { "esther" }

        it("or after declaration") {
          age {22}
          assertThat(age()).isEqualTo(22)
          assertThat(name()).isEqualTo("esther")
        }

        describe("when using nested contexts") {
          name { "nested esther" }

          it("can access outer variables") {
            assertThat(age()).isEqualTo(23)
          }

          it("can re define the value in the subcontext") {
            assertThat(name()).isEqualTo("nested esther")
          }
        }
      }
    }
  }
}
```


Based on [Jasmine](http://jasmine.github.io/) for javascript and [RSpec](http://rspec.info/) for Ruby  
Adds the ability to used type variables on `let` definitions  

## **[See more details in the wiki](https://github.com/kfgodel/java-spec/wiki)**


### Maven dependency ###

* Declare the dependency
```
<dependency>
  <groupId>info.kfgodel</groupId>
  <artifactId>java-spec</artifactId>
  <version>2.5.2</version>
  <scope>test</scope>
</dependency>
```

### Junit error
If you get this error:
```
java.lang.NoSuchMethodError: org.junit.runner.Description.createSuiteDescription(Ljava/lang/String;Ljava/io/Serializable;[Ljava/lang/annotation/Annotation;)Lorg/junit/runner/Description;
    at ar.com.dgarcia.javaspec.impl.junit.JunitTestTreeAdapter.recursiveAdaptToJunit(JunitTestTreeAdapter.java:48)
    at ar.com.dgarcia.javaspec.impl.junit.JunitTestTreeAdapter.adaptToJunit(JunitTestTreeAdapter.java:39)
    at ar.com.dgarcia.javaspec.impl.junit.JunitTestTreeAdapter.create(JunitTestTreeAdapter.java:27)
```
The problem is the junit version. You need at least version **4.11** that exposes the Description object which is needed for JavaSpec to describe the tests.  

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

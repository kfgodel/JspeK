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
class LetSpecTest : KotlinSpec() {

  override fun define() {
    describe("lets") {

      describe("can be declared in suite contexts") {
        val predefinedValue by let { 3 }

        it("can have a value defined with its creation") {
          assertThat(predefinedValue.get()).isEqualTo(3)
        }

        val foo: TestVariable<Int> by let()
        describe("and its value can be set in contexts") {
          foo.set { 1 }

          it("can obtain that value") {
            assertThat(foo.get()).isEqualTo(1)
          }

          describe("when redefining its value in a sub-context") {
            foo.set { 2 }

            it("changes the original value") {
              assertThat(foo.get()).isEqualTo(2)
            }
          }

        }

        it("and its value can also be set inside a test") {
          foo.set { 3 }

          assertThat(foo.get()).isEqualTo(3)
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

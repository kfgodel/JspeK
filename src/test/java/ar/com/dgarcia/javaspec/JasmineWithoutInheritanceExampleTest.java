package ar.com.dgarcia.javaspec;

import ar.com.dgarcia.javaspec.api.*;
import org.junit.runner.RunWith;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This class serves as an example of Java-Spec without using inheritance to define the spec.<br>
 * Based on http://jasmine.github.io/2.0/introduction.html
 * Created by kfgodel on 12/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class JasmineWithoutInheritanceExampleTest implements JavaSpecTestable<TestContext> {

  private Variable<Integer> foo = new Variable<>();
  private Variable<String> bar = new Variable<>();

  public void defineSpecs(JavaSpecApi<TestContext> spec) {

    // introduction.js
    spec.describe("A suite", () -> {
      spec.it("contains spec with an expectation", () -> {
        assertThat(true).isEqualTo(true);
      });
    });


    // It’s Just Functions
    spec.describe("A suite is just a lambda", () -> {

      Variable<Boolean> a = Variable.create();

      spec.it("and so is a spec", () -> {
        a.set(true);

        assertThat(a.get()).isEqualTo(true);
      });
    });


    // Expectations
    spec.describe("AssertJ assertions can be used in the spec", () -> {

      spec.it("and has a positive case", () -> {
        assertThat(true).isEqualTo(true);
      });

      spec.it("and can have a negative case", () -> {
        assertThat(false).isNotEqualTo(true);
      });
    });


    // Setup and Teardown
    spec.describe("A spec (with setup and tear-down)", () -> {
      Variable<Integer> foo = Variable.create();

      spec.beforeEach(() -> {
        foo.set(0);
        foo.storeSumWith(1);
      });

      spec.afterEach(() -> {
        foo.set(0);
      });

      spec.it("is just a lambda, so it can contain any code", () -> {
        assertThat(foo.get()).isEqualTo(1);
      });

      spec.it("can have more than one expectation", () -> {
        assertThat(foo.get()).isEqualTo(1);
        assertThat(true).isEqualTo(true);
      });

    });


    spec.describe("A spec", () -> {
      Variable<Integer> foo = new Variable<>();

      spec.beforeEach(() -> {
        foo.set(0);
        foo.storeSumWith(1);
      });

      spec.afterEach(() -> {
        foo.set(0);
      });

      spec.it("is just a lambda, so it can contain any code", () -> {
        assertThat(foo.get()).isEqualTo(1);
      });

      spec.it("can have more than one expectation", () -> {
        assertThat(foo.get()).isEqualTo(1);
        assertThat(true).isTrue();
      });

      spec.describe("nested inside a second describe", () -> {
        Variable<Integer> bar = new Variable<>();

        spec.beforeEach(() -> {
          bar.set(1);
        });

        spec.it("can reference both scopes as needed", () -> {
          assertThat(foo.get()).isEqualTo(bar.get());
        });
      });
    });


    // The this keyword
    spec.describe("A spec", () -> {
      spec.beforeEach(() -> {
        this.foo.set(0);
      });

      spec.it("that use `this` to share state", () -> {
        assertThat(foo.get()).isEqualTo(0);
        this.bar.set("test pollution?");
      });

      spec.it("will have test pollution by sharing the host instance", () -> {
        assertThat(foo.get()).isEqualTo(0);
        assertThat(bar.get()).isEqualTo("test pollution?");
      });
    });


    // Disabling Suites
    spec.xdescribe("A disabled group spec", () -> {

      spec.it("disabled implicitly", () -> {
        assertThat(foo.get()).isEqualTo(1);
      });

      spec.xit("disabled explicitly", () -> {
        assertThat(foo.get()).isEqualTo(2);
      });
    });


    // Pending Specs
    spec.describe("Pending specs", () -> {

      spec.xit("can be declared 'xit'", () -> {
        assertThat(true).isEqualTo(false);
      });

      spec.it("can be declared with 'it' but without a lambda");

    });


    // Spies
    spec.describe("has spies with mockito", () -> {

      spec.it("use mockito as usuarl", () -> {
        List<String> mockedList = mock(List.class);
        when(mockedList.get(0)).thenReturn("First");

        assertThat(mockedList.get(0)).isEqualTo("First");
      });

    });

  }

}

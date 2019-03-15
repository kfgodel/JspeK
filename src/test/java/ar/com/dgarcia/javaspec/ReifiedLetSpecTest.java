package ar.com.dgarcia.javaspec;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.dgarcia.javaspec.api.contexts.TestContext;
import ar.com.dgarcia.javaspec.api.variable.Let;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JavaSpecRunner.class)
public class ReifiedLetSpecTest extends JavaSpec<TestContext> {

  private Let<Integer> foo = let("foo");

  private Let<Integer> let(String name) {
    return Let.create(name, this::context);
  }

  @Override
  public void define() {
    describe("when using a let", () -> {

      describe("and setting its value", () -> {
        foo.set(() -> 1);
        it("can obtain that value",()->{
          assertThat(foo.get()).isEqualTo(1);
        });
      });

    });

  }
}

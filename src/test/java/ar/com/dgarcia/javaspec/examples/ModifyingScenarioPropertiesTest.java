package ar.com.dgarcia.javaspec.examples;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import com.google.common.collect.Lists;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 08/09/18 - 22:54
 */
@RunWith(JavaSpecRunner.class)
public class ModifyingScenarioPropertiesTest extends JavaSpec<ExampleTestContext> {
  @Override
  public void define() {
    describe("adding elements to a collection", () -> {
      beforeEach(() -> {
        context().collection().addAll(context().elements());
      });

      describe("when the collection is a list", () -> {
        context().collection(ArrayList::new);

        describe("and the elements are unsorted", () -> {
          context().elements(() -> Lists.newArrayList("2", "3", "1"));

          it("keeps the original unsorted order", () -> {
            assertThat(context().collection()).isEqualTo(Lists.newArrayList("2", "3", "1"));
          });
        });
      });

      describe("when the collection is a sorted set", () -> {
        context().collection(TreeSet::new);

        describe("and the elements are unsorted", () -> {
          context().elements(() -> Lists.newArrayList("2", "3", "1"));

          it("changes the order according to sorting criteria", () -> {
            assertThat(new ArrayList<>(context().collection())).isEqualTo(Lists.newArrayList("1", "2", "3"));
          });
        });
      });

    });
  }
}
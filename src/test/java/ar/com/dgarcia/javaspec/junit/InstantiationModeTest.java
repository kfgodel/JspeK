package ar.com.dgarcia.javaspec.junit;

import ar.com.dgarcia.javaspec.api.exceptions.SpecException;
import ar.com.dgarcia.javaspec.examples.MinimumTest;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This test verifies that the initial state of a spec throws appropiate errors
 * Date: 30/03/19 - 16:24
 */
public class InstantiationModeTest {

  private MinimumTest spec;

  @Before
  public void createSpec(){
    this.spec = new MinimumTest();
  }

  @Test
  public void itCantAccessTheContext(){
    try {
      this.spec.context();
    } catch (SpecException e) {
      assertThat(e).hasMessage("The context is not available outside the method define");
    }
  }

  @Test
  public void itCantDefineATest(){
    try {
      this.spec.it("a wrongly defined test", ()-> {});
    } catch (SpecException e) {
      assertThat(e).hasMessage("A test can't be defined outside the method define");
    }
  }
  @Test
  public void itCantDefineGroups(){
    try {
      this.spec.describe("a wrongly defined group", ()-> {});
    } catch (SpecException e) {
      assertThat(e).hasMessage("A group can't be defined outside the method define");
    }
  }

}

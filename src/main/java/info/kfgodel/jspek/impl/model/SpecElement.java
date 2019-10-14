package info.kfgodel.jspek.impl.model;

import java.util.List;
import java.util.function.Consumer;

/**
 * This type represents a java spec element that has a name
 * Created by kfgodel on 12/07/14.
 */
public interface SpecElement {

  /**
   * The name identifying this element
   *
   * @return The name given by definition of this element
   */
  String getName();

  /**
   * Returns the ordered runnables that represent code blocks to execute before to the test.<br>
   * Inherited blocks are first
   *
   * @return The list of inherited before blocks
   */
  List<Runnable> getBeforeBlocks();

  /**
   * Returns the ordered runnables that represent code blocks to execute after the test.<br>
   * Inherited blocks are last
   *
   * @return The list of inherited after blocks
   */
  List<Runnable> getAfterBlocks();

  /**
   * Allows executing conditional code without knowing the type of this element.<br>
   *   If this is a group then the code is executed, otherwise it's ignored
   * @param codeOnAGroup The code to execute that will receive this instance as a group
   * @return This instance for method chaining
   */
  SpecElement ifItIsAGroup(Consumer<SpecGroup> codeOnAGroup);

  /**
   * Allows executing conditional code without knowing the type of this element.<br>
   *   If this is a test then the code is executed, otherwise it's ignored
   * @param codeOnATest The code to execute that will receive this instance as a test
   * @return This instance for method chaining
   */
  SpecElement ifItIsATest(Consumer<SpecTest> codeOnATest);
}

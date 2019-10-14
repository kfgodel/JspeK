package info.kfgodel.jspek.api.exceptions;

/**
 * This type represents the base clase of all JavaSpec exceptions
 * Created by kfgodel on 12/07/14.
 */
public class SpecException extends RuntimeException {
  /**
   * Creates a new instance without a previous cause
   * @param message The error message to describe what went wrong
   */
  public SpecException(String message) {
    super(message);
  }

  /**
   * Creates a new instance from a previous cause
   * @param message The error message to describe what went wrong
   * @param cause The original error for this to happen
   */
  public SpecException(String message, Throwable cause) {
    super(message, cause);
  }

}

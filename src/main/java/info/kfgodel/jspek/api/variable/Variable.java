package info.kfgodel.jspek.api.variable;

/**
 * This type represents a variable that can be used to share variables between lambdas
 *
 * @param <T> Type of the value referenced by this variable
 *            Created by kfgodel on 12/07/14.
 */
public class Variable<T> {

  private T value;


  /**
   * Creates an empty variable referencing null
   *
   * @param <T> The type of expected values
   * @return Newly created variable
   */
  public static <T> Variable<T> create() {
    return Variable.of(null);
  }

  /**
   * @return The value on this variables
   */
  public T get() {
    return value;
  }

  /**
   * Generates a new variable with an initial value
   *
   * @param initialValue The value to be referenced
   * @param <T>          The type of value
   * @return The created instance
   */
  public static <T> Variable<T> of(T initialValue) {
    Variable<T> variable = new Variable<>();
    variable.set(initialValue);
    return variable;
  }

  /**
   * Sets the value on this variable changing previous references
   *
   * @param newValue The new value to be assigned
   * @return This variable for method chaining
   */
  public Variable<T> set(T newValue) {
    this.value = newValue;
    return this;
  }

  /**
   * Removes the reference to previous value and uses null instead
   *
   * @return This instance
   */
  public Variable<T> clean() {
    return set(null);
  }

  /**
   * Sums the given value as a number or string with the current value and stores the result.<br>
   * This is a facility method to reduce verbosity. It sums numbers and appends strings
   *
   * @param operand The operand to sum with the current value
   * @return This variable
   * @throws UnsupportedOperationException If the given operand does not represent a number
   */
  @SuppressWarnings("unchecked")
  public Variable<T> storeSumWith(T operand) throws UnsupportedOperationException {
    if (this.value instanceof Integer) {
      Integer currentValue = (Integer) this.value;
      int result = currentValue + ((Number) operand).intValue();
      this.value = (T) Integer.valueOf(result);
    } else if (this.value instanceof Double) {
      Double currentValue = (Double) this.value;
      double result = currentValue + ((Number) operand).doubleValue();
      this.value = (T) Double.valueOf(result);
    } else if (this.value instanceof String) {
      String currentValue = (String) this.value;
      String result = currentValue + operand;
      this.value = (T) result;
    } else {
      throw new UnsupportedOperationException("Sum is not supported for value [" + this.value +
        "] and operand [" + operand + "]");
    }
    return this;
  }

  @Override
  public String toString() {
    return String.valueOf(this.value);
  }

  /**
   * Returns the value casted to expected type using generics
   *
   * @param expectedClass Type of expected result
   * @param <R>           The type of returned value
   * @return The value casted to the type indicated. Will fail if not of that type
   */
  public <R> R castedTo(Class<R> expectedClass) {
    return expectedClass.cast(get());
  }
}

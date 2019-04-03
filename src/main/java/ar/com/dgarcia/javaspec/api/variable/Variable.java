package ar.com.dgarcia.javaspec.api.variable;

/**
 * This type represents a variable that can be used to share variables between lambdas
 * Created by kfgodel on 12/07/14.
 */
public class Variable<T> {

    private T value;


    public static <T> Variable<T> create() {
        return Variable.of(null);
    }

    public T get() {
        return value;
    }

    public static <T> Variable<T> of(T initialValue) {
        Variable<T> variable = new Variable<>();
        variable.set(initialValue);
        return variable;
    }

    public Variable<T> set(T newValue) {
        this.value = newValue;
        return this;
    }

    public Variable<T> clean() {
        this.value = null;
        return this;
    }

    @SuppressWarnings("unchecked")
	public Variable<T> storeSumWith(T operand) throws UnsupportedOperationException {
        if(this.value instanceof Integer){
            Integer currentValue = (Integer) this.value;
            int result = currentValue + ((Number) operand).intValue();
            this.value = (T)Integer.valueOf(result);
        }else if(this.value instanceof Double){
            Double currentValue = (Double) this.value;
            double result = currentValue + ((Number) operand).doubleValue();
            this.value = (T)Double.valueOf(result);
        }else if(this.value instanceof String){
            String currentValue = (String) this.value;
            String result = currentValue + operand;
            this.value = (T)result;
        }else{
            throw new UnsupportedOperationException("Sum is not supported for value ["+this.value+"] and operand ["+operand+"]");
        }
        return this;
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

    /**
     * Returns the value casted to expected type using generics
     * @param expectedClass Type of expected result
     * @param <R> The type of returned value
     * @return The value casted to the type indicated. Will fail if not of that type
     */
    @SuppressWarnings("unchecked")
	public<R> R castedTo(Class<R> expectedClass) {
        return (R) get();
    }
}

package ar.com.dgarcia.javaspec.api;

/**
 * This type represents a variable that can be used to share variables between lambdas
 * Created by kfgodel on 12/07/14.
 */
public class Variable<T> {

    private T value;


    public static <T> Variable<T> create() {
        Variable<T> variable = new Variable<>();
        variable.value = null;
        return variable;
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
            String result = currentValue + ((CharSequence) operand);
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
}

package ar.com.dgarcia.javaspec.api.exceptions;

/**
 * This type represents the base clase of all JavaSpec exceptions
 * Created by kfgodel on 12/07/14.
 */
public class SpecException extends RuntimeException {
	private static final long serialVersionUID = -3243397993932692864L;

	public SpecException(String message) {
        super(message);
    }

    public SpecException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpecException(Throwable cause) {
        super(cause);
    }
}

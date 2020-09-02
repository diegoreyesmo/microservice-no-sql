package diegoreyesmo.springboot.nosql.exception;

public class NoSqlException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoSqlException(String message) {
		super(message);
	}

	public NoSqlException(String message, Throwable cause) {
		super(message, cause);
	}

}

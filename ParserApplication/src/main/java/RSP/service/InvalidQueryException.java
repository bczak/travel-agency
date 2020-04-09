package RSP.service;

public class InvalidQueryException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidQueryException(String argument, Integer value) {
		super("Invalid query argument " + argument + "=" + value);
	}
}

package RSP.service;

public class InconsistentQueryException extends Exception {

	private static final long serialVersionUID = 1L;

	public <T> InconsistentQueryException(
		String argument1, T value1, String argument2, T value2) {

		super("Inconsistent requirements: "
				+ argument1 + "=" + value1 + " AND " + argument2 + "=" + value2);
	}
}

package RSP.service;

public class InconsistentQueryException extends Exception {

	private static final long serialVersionUID = 1L;

	public InconsistentQueryException(
		String argument1, Integer minPrice, String argument2, Integer maxPrice) {

		super("Inconsistent requirements: "
				+ argument1 + "=" + minPrice + " AND " + argument2 + "=" + maxPrice);
	}
}

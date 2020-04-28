package RSP.service;

public class TripCriteriaNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public TripCriteriaNotFoundException(int id) {
		super("No Trip Criteria with id = " + id);
	}
}

package RSP.service;

public class RecommendationNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public RecommendationNotFoundException(int id) {
		super("No Recommendation with id = " + id);
	}
}

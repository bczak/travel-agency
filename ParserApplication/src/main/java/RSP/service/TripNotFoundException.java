package RSP.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TripNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public TripNotFoundException(int id) {
		super("No Trip with id = " + id);
	}

	public TripNotFoundException(String name) {
		super("No Trip with name = " + name);
	}
}

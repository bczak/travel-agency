package RSP.service;

public class TripNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public TripNotFoundException(int id) {
        super("No Trip with id = " + id);
    }

    public TripNotFoundException(String name) {
        super("No Trip with name = " + name);
    }
}

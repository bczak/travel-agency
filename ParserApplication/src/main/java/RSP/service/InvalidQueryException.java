package RSP.service;

public class InvalidQueryException extends Exception {

    private static final long serialVersionUID = 1L;

    public <T> InvalidQueryException(String argument, T value) {
        super("Invalid query argument " + argument + "=" + value);
    }
}

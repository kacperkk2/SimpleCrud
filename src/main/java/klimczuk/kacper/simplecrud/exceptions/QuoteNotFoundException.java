package klimczuk.kacper.simplecrud.exceptions;

public class QuoteNotFoundException extends RuntimeException {

    public QuoteNotFoundException(long id) {
        super("Quote with id " + id + " not found");
    }
}

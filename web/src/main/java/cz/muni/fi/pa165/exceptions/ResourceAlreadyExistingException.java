package cz.muni.fi.pa165.exceptions;

/**
 * Exception converted by MyExceptionHandler to UNPROCESSABLE_ENTITY HTTP status.
 */
public class ResourceAlreadyExistingException extends RuntimeException {

    public ResourceAlreadyExistingException(String message) {
        super(message);
    }
}

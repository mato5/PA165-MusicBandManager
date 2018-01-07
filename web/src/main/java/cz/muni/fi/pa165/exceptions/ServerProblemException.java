package cz.muni.fi.pa165.exceptions;

/**
 * Exception converted by MyExceptionHandler to INTERNAL_SERVER_ERROR HTTP status.
 */
public class ServerProblemException extends RuntimeException {

    public ServerProblemException(String message) {
        super(message);
    }
}

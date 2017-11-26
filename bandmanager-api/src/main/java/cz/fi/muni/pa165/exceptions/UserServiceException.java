package cz.fi.muni.pa165.exceptions;

/**
 * An exception invoked by the Manager or Member services.
 *
 * @author Matej Sojak 433294
 */
public class UserServiceException extends RuntimeException {

    public UserServiceException() {
        super();
    }

    public UserServiceException(String message, Throwable cause,
                                boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public UserServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserServiceException(String message) {
        super(message);
    }

    public UserServiceException(Throwable cause) {
        super(cause);
    }
}

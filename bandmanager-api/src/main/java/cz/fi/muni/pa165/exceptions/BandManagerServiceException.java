package cz.fi.muni.pa165.exceptions;

/**
 * @author Iurii xkuznetc Kuznetcov
 */

public class BandManagerServiceException extends RuntimeException {

    public BandManagerServiceException() {
        super();
    }

    public BandManagerServiceException(String message, Throwable cause,
                                       boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public BandManagerServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public BandManagerServiceException(String message) {
        super(message);
    }

    public BandManagerServiceException(Throwable cause) {
        super(cause);
    }

}
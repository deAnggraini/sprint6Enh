package id.co.bca.pakar.be.doc.exception;

/**
 *
 */
public class WfApiClientException extends Exception {
    public WfApiClientException(String message) {
        super(message);
    }

    public WfApiClientException(String message, Throwable cause) {
        super(message, cause);
    }
}

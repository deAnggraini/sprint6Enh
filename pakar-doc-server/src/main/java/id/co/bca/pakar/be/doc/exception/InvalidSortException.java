package id.co.bca.pakar.be.doc.exception;

public class InvalidSortException extends Exception {
    public InvalidSortException(String message) {
        super(message);
    }

    public InvalidSortException(String message, Throwable cause) {
        super(message, cause);
    }
}

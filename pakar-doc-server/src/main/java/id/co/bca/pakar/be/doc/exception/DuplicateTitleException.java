package id.co.bca.pakar.be.doc.exception;

public class DuplicateTitleException extends Exception {
    public DuplicateTitleException(String message) {
        super(message);
    }

    public DuplicateTitleException(String message, Throwable cause) {
        super(message, cause);
    }
}

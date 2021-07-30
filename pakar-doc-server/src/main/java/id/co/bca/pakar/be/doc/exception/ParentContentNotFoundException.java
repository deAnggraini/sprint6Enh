package id.co.bca.pakar.be.doc.exception;

public class ParentContentNotFoundException extends Exception {
    public ParentContentNotFoundException(String message) {
        super(message);
    }

    public ParentContentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

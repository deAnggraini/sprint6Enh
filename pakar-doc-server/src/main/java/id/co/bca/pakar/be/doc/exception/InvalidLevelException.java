package id.co.bca.pakar.be.doc.exception;

public class InvalidLevelException extends Exception {
    public InvalidLevelException(String message) {
        super(message);
    }

    public InvalidLevelException(String message, Throwable cause) {
        super(message, cause);
    }
}

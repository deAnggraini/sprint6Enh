package id.co.bca.pakar.be.wf.exception;

public class UndefinedStartedStateException extends Exception {
    public UndefinedStartedStateException(String message) {
        super(message);
    }

    public UndefinedStartedStateException(String message, Throwable cause) {
        super(message, cause);
    }
}

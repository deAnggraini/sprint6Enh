package id.co.bca.pakar.be.wf.exception;

public class UndefinedProcessException extends Exception {
    public UndefinedProcessException(String message) {
        super(message);
    }

    public UndefinedProcessException(String message, Throwable cause) {
        super(message, cause);
    }
}

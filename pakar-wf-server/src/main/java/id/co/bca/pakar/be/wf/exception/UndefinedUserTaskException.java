package id.co.bca.pakar.be.wf.exception;

public class UndefinedUserTaskException extends Exception {
    public UndefinedUserTaskException(String message) {
        super(message);
    }

    public UndefinedUserTaskException(String message, Throwable cause) {
        super(message, cause);
    }
}

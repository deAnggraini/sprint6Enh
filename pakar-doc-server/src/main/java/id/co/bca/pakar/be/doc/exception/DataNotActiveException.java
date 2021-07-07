package id.co.bca.pakar.be.doc.exception;

public class DataNotActiveException extends Exception {
    public DataNotActiveException(String message) {
        super(message);
    }

    public DataNotActiveException(String message, Throwable cause) {
        super(message, cause);
    }
}

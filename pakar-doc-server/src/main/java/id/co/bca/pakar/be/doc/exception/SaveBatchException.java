package id.co.bca.pakar.be.doc.exception;

public class SaveBatchException extends Exception {
    public SaveBatchException(String message) {
        super(message);
    }

    public SaveBatchException(String message, Throwable cause) {
        super(message, cause);
    }
}

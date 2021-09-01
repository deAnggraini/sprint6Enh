package id.co.bca.pakar.be.doc.exception;

public class UndefinedStructureException extends Exception {
    public UndefinedStructureException(String message) {
        super(message);
    }

    public UndefinedStructureException(String message, Throwable cause) {
        super(message, cause);
    }
}

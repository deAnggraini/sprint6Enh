package id.co.bca.pakar.be.doc.exception;

public class StructureNotFoundException extends Exception {
    public StructureNotFoundException(String message) {
        super(message);
    }

    public StructureNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

package id.co.bca.pakar.be.doc.exception;

public class AccesDeniedDeleteContentException extends Exception {
    public AccesDeniedDeleteContentException(String message) {
        super(message);
    }

    public AccesDeniedDeleteContentException(String message, Throwable cause) {
        super(message, cause);
    }
}

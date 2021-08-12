package id.co.bca.pakar.be.doc.exception;

public class AccesDeniedViewContentsException extends Exception{
    public AccesDeniedViewContentsException(String message) {
        super(message);
    }

    public AccesDeniedViewContentsException(String message, Throwable cause) {
        super(message, cause);
    }
}

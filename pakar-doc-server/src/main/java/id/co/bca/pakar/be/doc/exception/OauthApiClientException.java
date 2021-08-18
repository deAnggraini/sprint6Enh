package id.co.bca.pakar.be.doc.exception;

public class OauthApiClientException extends Exception {
    public OauthApiClientException(String message) {
        super(message);
    }

    public OauthApiClientException(String message, Throwable cause) {
        super(message, cause);
    }
}

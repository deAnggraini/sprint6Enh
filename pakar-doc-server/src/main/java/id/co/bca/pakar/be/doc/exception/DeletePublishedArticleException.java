package id.co.bca.pakar.be.doc.exception;

public class DeletePublishedArticleException extends Exception {
    public DeletePublishedArticleException(String message) {
        super(message);
    }

    public DeletePublishedArticleException(String message, Throwable cause) {
        super(message, cause);
    }
}

package id.co.bca.pakar.be.doc.exception;

public class NotFoundUserArticleEditingException extends Exception {
    public NotFoundUserArticleEditingException(String message) {
        super(message);
    }

    public NotFoundUserArticleEditingException(String message, Throwable cause) {
        super(message, cause);
    }
}

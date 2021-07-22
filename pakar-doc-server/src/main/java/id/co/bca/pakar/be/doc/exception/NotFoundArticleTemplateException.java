package id.co.bca.pakar.be.doc.exception;

public class NotFoundArticleTemplateException extends Exception {
    public NotFoundArticleTemplateException(String message) {
        super(message);
    }

    public NotFoundArticleTemplateException(String message, Throwable cause) {
        super(message, cause);
    }
}

package id.co.bca.pakar.be.doc.exception;

public class ArticleNotFoundException extends Exception {
    public ArticleNotFoundException(String message) {
        super(message);
    }

    public ArticleNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

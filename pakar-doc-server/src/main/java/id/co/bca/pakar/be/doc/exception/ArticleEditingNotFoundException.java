package id.co.bca.pakar.be.doc.exception;

public class ArticleEditingNotFoundException extends Exception {
    public ArticleEditingNotFoundException(String message) {
        super(message);
    }

    public ArticleEditingNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

package id.co.bca.pakar.be.doc.exception;

public class ArticleContentNotFoundException extends Exception {
    public ArticleContentNotFoundException(String message) {
        super(message);
    }

    public ArticleContentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

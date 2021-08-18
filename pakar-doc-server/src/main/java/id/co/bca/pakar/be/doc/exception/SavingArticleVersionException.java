package id.co.bca.pakar.be.doc.exception;

public class SavingArticleVersionException extends Exception {
    public SavingArticleVersionException(String message) {
        super(message);
    }

    public SavingArticleVersionException(String message, Throwable cause) {
        super(message, cause);
    }
}

package exception;

public class ProductNotFoundException extends ShoppingCartException{
    public ProductNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ProductNotFoundException(ErrorCode errorCode, String customMessage) {
        super(errorCode, customMessage);
    }
}

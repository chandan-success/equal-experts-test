package exception;

public class InvalidQuantityException extends ShoppingCartException{

    public InvalidQuantityException(ErrorCode errorCode) {
        super(errorCode);
    }

    public InvalidQuantityException(ErrorCode errorCode, String customMessage) {
        super(errorCode, customMessage);
    }
}

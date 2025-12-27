package exception;


/**
 * ## Exception Handling
 * Business exceptions represent domain errors (e.g. invalid quantity).
 * Technical exceptions wrap infrastructure failures (e.g. Price API).
 * Infrastructure details do not leak into domain logic.
 */

/**RuntimeException
 ├── ShoppingCartException
 │    ├── ProductNotFoundException
 │    ├── InvalidQuantityException
 │    └── CartEmptyException
 └── PriceServiceException**/


public class ShoppingCartException extends RuntimeException {
    private final ErrorCode errorCode;

    public ShoppingCartException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ShoppingCartException(ErrorCode errorCode, String customMessage) {
        super(customMessage); // other than defined message
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}

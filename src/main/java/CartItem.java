import java.math.BigDecimal;

/**
 * The cart contains the product, quantity and price.
 */
public class CartItem {
    private final String productName;
    private final BigDecimal unitPrice;
    private int quantity;

    public CartItem(String productName, int quantity, BigDecimal unitPrice) {
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;

    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    public BigDecimal totalPrice() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    public int getQuantity() {
        return quantity;
    }

    public String getProductName() {
        return productName;
    }
}

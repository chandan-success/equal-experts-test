import java.math.BigDecimal;
import java.util.Map;

/**
 * CartState is helping to its availability.
 */
public class CartState {
    private final Map<String, CartItem> cartItemMap;
    private final BigDecimal subtotal;
    private final BigDecimal tax;
    private final BigDecimal total;

    public CartState(BigDecimal subtotal, BigDecimal tax, BigDecimal total,  Map<String, CartItem> cartItemMap) {
        this.tax = tax;
        this.total = total;
        this.subtotal = subtotal;
        this.cartItemMap = cartItemMap;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public Map<String, CartItem> getCartItemMap() {
        return cartItemMap;
    }
}

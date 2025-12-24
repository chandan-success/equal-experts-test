import service.PriceService;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * Adding items to the cart.
 * Calculating the state
 */
public class ShoppingCart {

    private final Map<String, CartItem> items = new HashMap<>();

    private final PriceService priceService;
     ShoppingCart(PriceService priceService){
        this.priceService = priceService;
    }

    public void addToCart(String productName, int quantity) throws IOException, InterruptedException {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        CartItem existingItem = items.get(productName);

        // Prevent multiple call to price API for the same item.
        if (existingItem != null) {
            existingItem.addQuantity(quantity);
            return;
        }

        BigDecimal price = priceService.getPrice(productName);
        CartItem newItem = new CartItem(productName,quantity, price);
        items.put(productName, newItem);
    }

    public CartState calculateState() {
        BigDecimal subTotal = subTotal();
        BigDecimal tax = applyTax(subTotal);
        BigDecimal totalPrice = getTotalPrice(subTotal, tax);
        return new CartState(subTotal, tax, totalPrice, items);
    }

    public BigDecimal subTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item : items.values()) {
            total = total.add(item.totalPrice());
        }
        return total;
    }

    /**
     * Tax can be configurable
     * @param subTotal
     * @return
     */
    public BigDecimal applyTax(BigDecimal subTotal) {
        return subTotal.multiply(BigDecimal.valueOf(12.5)).divide(BigDecimal.valueOf(100), 2, RoundingMode.CEILING);
    }

    public BigDecimal getTotalPrice(BigDecimal subTotal, BigDecimal taxPayable){
        return subTotal.add(taxPayable);
    }
}

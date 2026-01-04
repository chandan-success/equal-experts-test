import exception.ErrorCode;
import exception.InvalidQuantityException;
import service.PriceService;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Adding items to the cart.
 * Calculating the state
 */
public class ShoppingCart {

    private final Map<String, CartItem> items = new HashMap<>();
    private final ConcurrentHashMap<String, CartItem> concurrentItems = new ConcurrentHashMap<>();

    private final PriceService priceService;
     ShoppingCart(PriceService priceService){
        this.priceService = priceService;
    }

    //This is not thread safe. Please discuss this how can we make this thread safe.(ConcurrentHashMap ensures thread safety for individual operations like get and put, but compound operations involving multiple steps still require atomic methods such as compute, merge, or putIfAbsent.)
    public void addToCart(String productName, int quantity) throws IOException, InterruptedException {
        if (quantity <= 0) {
            throw new InvalidQuantityException(ErrorCode.INVALID_QUANTITY);
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

    /** This method i have tried to make thread safe using concurrentHashMap. But there is tradeoff(kami in hindi).
     * The price api is getting called multiple times while adding same product. Inefficient approach.
     * In multi-threaded environment there will be un necessary extra object creation.(Thread t1, t2 both will be creating the object. Though at last one will win, but object was created twice.)
     * @param productName
     * @param quantity
     * @throws IOException
     * @throws InterruptedException
     */
    public void addToCartWithThreadSafeButInEfficient(String productName, int quantity) throws IOException, InterruptedException {
        if (quantity <= 0) {
            throw new InvalidQuantityException(ErrorCode.INVALID_QUANTITY);
        }

        BigDecimal price = priceService.getPrice(productName); // will be called for same product everytime.
        CartItem newItem = new CartItem(productName,quantity, price);
        CartItem existingItem = concurrentItems.putIfAbsent(productName, newItem); // if item already exist, it will return the existing item else null in case of new item added.
        if (existingItem != null) {
            existingItem.addQuantity(quantity);
        }
    }

    public void addToCartWithThreadSafeWithEfficiency(String productName, int quantity) {
        if (quantity <= 0) {
            throw new InvalidQuantityException(ErrorCode.INVALID_QUANTITY);
        }

        concurrentItems.compute(productName, (p,item)->{
            if(item != null){
                item.addQuantity(quantity);
                return item;
            }
            BigDecimal price = null; // will be called for same product everytime.
            try {
                price = priceService.getPrice(productName);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
            return new CartItem(productName,quantity, price);
        });

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

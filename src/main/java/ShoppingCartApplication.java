import impl.HttpPriceService;
import service.PriceService;

import java.io.IOException;

public class ShoppingCartApplication {
    public static void main(String[] args) throws IOException, InterruptedException {
        PriceService priceService = new HttpPriceService();
        ShoppingCart shoppingCart = new ShoppingCart(priceService);
        shoppingCart.addToCart("cornflakes", 1);
        shoppingCart.addToCart("cornflakes", 2);
        shoppingCart.addToCart("weetabix", 1);

        CartState cartState = shoppingCart.calculateState();
        cartState.getCartItemMap().values().forEach(ci-> {
            System.out.println("Cart contains " + ci.getQuantity() + " " +  ci.getProductName());
        });
        System.out.println("Subtotal = " + cartState.getSubtotal());
        System.out.println("Tax = " + cartState.getTax());
        System.out.println("Total = " + cartState.getTotal());
    }
}

import impl.HttpPriceService;
import org.junit.jupiter.api.Test;
import service.PriceService;
import service.DummayPriceService;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShoppingCartTest {

    @Test
    void calculatesSubtotalTest() throws IOException, InterruptedException {
        PriceService priceService = new DummayPriceService();
        ShoppingCart cart = new ShoppingCart(priceService);

        cart.addToCart("cornflakes", 2);
        cart.addToCart("weetabix", 1);
        BigDecimal subTotal = cart.subTotal();
        assertEquals(new BigDecimal("15.02"), subTotal);
    }

    @Test
    void calculateTaxOnSubTotalTest() throws IOException, InterruptedException {
        PriceService priceService = new DummayPriceService();
        ShoppingCart cart = new ShoppingCart(priceService);

        cart.addToCart("cornflakes", 2);
        cart.addToCart("weetabix", 1);
        BigDecimal subTotal = cart.subTotal();
        BigDecimal tax = cart.applyTax(subTotal);
        assertEquals(new BigDecimal("1.88"), tax);
    }

    @Test
    void calculateTotalTest() throws IOException, InterruptedException {
        PriceService priceService = new DummayPriceService();
        ShoppingCart cart = new ShoppingCart(priceService);

        cart.addToCart("cornflakes", 2);
        cart.addToCart("weetabix", 1);
        BigDecimal subTotal = cart.subTotal();
        BigDecimal tax = cart.applyTax(subTotal);
        BigDecimal totalPrice = cart.getTotalPrice(subTotal, tax);
        assertEquals(new BigDecimal("16.90"), totalPrice);
    }

    @Test
    void clientFetchesPriceTest() throws IOException, InterruptedException {
        PriceService priceService = new HttpPriceService();
        BigDecimal price = priceService.getPrice("cornflakes");
        assertEquals(new BigDecimal("2.52"), price);
    }
}



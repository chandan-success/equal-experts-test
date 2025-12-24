package service;

import java.math.BigDecimal;

/**
 * This class is introduced to serve the mocking of price API.
 */
public class DummayPriceService implements PriceService {

    @Override
    public BigDecimal getPrice(String product) {
        return switch (product) {
            case "cornflakes" -> new BigDecimal("2.52");
            case "weetabix" -> new BigDecimal("9.98");
            default -> BigDecimal.ZERO;
        };
    }
}

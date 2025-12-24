package service;

import java.io.IOException;
import java.math.BigDecimal;

public interface PriceService {
    BigDecimal getPrice(String product) throws IOException, InterruptedException;
}

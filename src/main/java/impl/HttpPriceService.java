package impl;

import service.PriceService;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpPriceService implements PriceService {

    private final HttpClient client = HttpClient.newHttpClient();
    @Override
    public BigDecimal getPrice(String product) throws IOException, InterruptedException {
        String url = "https://equalexperts.github.io/backend-take-home-test-data/"
                + product + ".json";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to fetch price");
        }

        // VERY simple JSON parsing since structure is fixed
        String body = response.body();
        String pricePart = body.split("\"price\"")[1];
        String priceValue = pricePart.split(":")[1].replace("}", "").trim();

        return new BigDecimal(priceValue);
    }
}

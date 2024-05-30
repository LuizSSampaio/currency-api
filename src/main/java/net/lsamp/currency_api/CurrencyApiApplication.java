package net.lsamp.currency_api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.atomic.AtomicReference;

@SpringBootApplication
@RestController
public class CurrencyApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyApiApplication.class, args);
	}

	@GetMapping("/currency")
	public BigDecimal currency(
			@RequestParam(value = "currencyCode") String currencyCode,
			@RequestParam(value = "value") BigDecimal value,
			@RequestParam(value = "targetCurrencyCode") String targetCurrencyCode
	) {
		currencyCode = currencyCode.toLowerCase();
		targetCurrencyCode = targetCurrencyCode.toLowerCase();

		String apiUrl = String.format(
				"https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies/%s.json",
				currencyCode
		);
		AtomicReference<Double> exchangeRate = new AtomicReference<>((double) 0);

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(apiUrl)).build();
		String finalCurrencyCode = currencyCode;
		String finalTargetCurrencyCode = targetCurrencyCode;
		client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
				.thenApply(HttpResponse::body)
				.thenAccept(responseBody -> {
					try {
						ObjectMapper mapper = new ObjectMapper();
						JsonNode rootNode = mapper.readTree(responseBody);
						JsonNode currencyNode = rootNode.path(finalCurrencyCode).path(finalTargetCurrencyCode);
						exchangeRate.set(currencyNode.asDouble());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}).join();

        return value.multiply(BigDecimal.valueOf(exchangeRate.get()));
	}

}

package com.alfonsocanal.analisis.services.marketdata;

import com.alfonsocanal.analisis.entities.RegistroMedida;

import com.alfonsocanal.analisis.entities.Empresa;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.Map;

@Component
public class MarketDataClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final String apiKey;

    public MarketDataClient(
            @Value("${alphavantage.base.url}") String baseUrl,
            @Value("${alphavantage.api.key}") String apiKey
    ) {
        this.restTemplate = new RestTemplate();
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
    }
    
    
    @SuppressWarnings("unchecked")
    public Map<String, Object> getDailyTimeSeries(String ticker) {

        String url = baseUrl +
                "?function=TIME_SERIES_DAILY" +
                "&symbol=" + ticker +
                "&outputsize=compact" +
                "&apikey=" + apiKey;

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        if (response == null) {
            throw new MarketDataException("Respuesta nula de Alpha Vantage");
        }

        if (response.containsKey("Error Message")) {
            throw new MarketDataException(response.get("Error Message").toString());
        }

        if (response.containsKey("Information")) {
            throw new MarketDataException(response.get("Information").toString());
        }

        if (!response.containsKey("Time Series (Daily)")) {
            throw new MarketDataException(
                "Nodo 'Time Series (Daily)' no presente en la respuesta"
            );
        }

        return (Map<String, Object>) response.get("Time Series (Daily)");
    }

    
    public Map<String, Object> getRsi(String ticker) {
        String url = baseUrl +
                "?function=RSI" +
                "&symbol=" + ticker +
                "&interval=daily" +
                "&time_period=14" +
                "&series_type=close" +
                "&apikey=" + apiKey;

        return callApi(url, "Technical Analysis: RSI");
    }

    public Map<String, Object> getEma(String ticker) {
        String url = baseUrl +
                "?function=EMA" +
                "&symbol=" + ticker +
                "&interval=daily" +
                "&time_period=14" +
                "&series_type=close" +
                "&apikey=" + apiKey;

        return callApi(url, "Technical Analysis: EMA");
    }

    public Map<String, Object> getSma(String ticker) {
        String url = baseUrl +
                "?function=SMA" +
                "&symbol=" + ticker +
                "&interval=daily" +
                "&time_period=14" +
                "&series_type=close" +
                "&apikey=" + apiKey;

        return callApi(url, "Technical Analysis: SMA");
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> callApi(String url, String expectedNode) {
    	
    	rateLimit();
    	
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        if (response == null) {
            throw new MarketDataException("Respuesta nula de Alpha Vantage");
        }

        // Errores típicos de Alpha Vantage
        if (response.containsKey("Error Message")) {
            throw new MarketDataException(response.get("Error Message").toString());
        }

        if (response.containsKey("Information")) {
            throw new MarketDataException(response.get("Information").toString());
        }

        if (!response.containsKey(expectedNode)) {
            throw new MarketDataException(
                "Nodo esperado '" + expectedNode + "' no presente en la respuesta"
            );
        }

        return (Map<String, Object>) response.get(expectedNode);
    }
    
    private void rateLimit() {
        try {
            Thread.sleep(1100); // 1.1 segundos
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}


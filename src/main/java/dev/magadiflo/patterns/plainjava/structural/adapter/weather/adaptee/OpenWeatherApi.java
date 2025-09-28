package dev.magadiflo.patterns.plainjava.structural.adapter.weather.adaptee;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.magadiflo.patterns.plainjava.structural.adapter.weather.adaptee.dto.json.OpenWeatherResponse;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@Slf4j
public class OpenWeatherApi {
    private static final String API_KEY = "1e795276e48658194d444a8b37e77bfc";
    private static final HttpClient httpClient = HttpClient.newHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();


    public Optional<OpenWeatherResponse> fetchWeather(String city) {
        try {
            String url = this.buildUrl(city);
            log.info("Consultando clima en JSON: {}", url);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                log.warn("Respuesta no exitosa: {}", response.statusCode());
                return Optional.empty();
            }

            String json = response.body();
            OpenWeatherResponse weather = objectMapper.readValue(json, OpenWeatherResponse.class);
            return Optional.of(weather);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // ✅ preserva la señal de interrupción
            log.error("Consulta interrumpida para {}: {}", city, e.getMessage(), e);
            return Optional.empty();
        } catch (Exception e) {
            log.error("Error al consultar clima para {}: {}", city, e.getMessage(), e);
            return Optional.empty();
        }
    }


    private String buildUrl(String city) {
        return "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric".formatted(city, API_KEY);
    }
}

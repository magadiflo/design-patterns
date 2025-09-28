package dev.magadiflo.patterns.springboot.structural.adapter.weather.client;

import dev.magadiflo.patterns.springboot.structural.adapter.weather.dto.json.OpenWeatherResponse;
import dev.magadiflo.patterns.springboot.structural.adapter.weather.exception.ExternalApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Slf4j
@Component
public class OpenWeatherApiClient {

    private static final String API_KEY = "1e795276e48658194d444a8b37e77bfc";
    private final RestClient restClient;

    public OpenWeatherApiClient(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("https://api.openweathermap.org").build();
    }

    public OpenWeatherResponse fetchWeather(String city) {
        try {
            return this.restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/data/2.5/weather")
                            .queryParam("q", city)
                            .queryParam("appid", API_KEY)
                            .queryParam("units", "metric")
                            .build())
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                        log.warn("Ciudad no encontrada: {}", city);
                        throw new ExternalApiException("Ciudad no encontrada: " + city);
                    })
                    .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                        log.error("Error interno en OpenWeather API");
                        throw new ExternalApiException("Error en el proveedor climático");
                    })
                    .body(OpenWeatherResponse.class);
        } catch (RestClientException e) {
            log.error("Error al consumir OpenWeather: {}", e.getMessage());
            throw new ExternalApiException("No se pudo conectar con el proveedor climático: " + e.getMessage());
        }
    }
}

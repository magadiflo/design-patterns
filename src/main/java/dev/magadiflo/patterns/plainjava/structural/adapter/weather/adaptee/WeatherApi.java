package dev.magadiflo.patterns.plainjava.structural.adapter.weather.adaptee;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import dev.magadiflo.patterns.plainjava.structural.adapter.weather.adaptee.dto.xml.WeatherResponse;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
public class WeatherApi {

    private static final String API_KEY = "678b1e8441044e6db3054310252109";
    private static final HttpClient httpClient = HttpClient.newHttpClient();
    private static final XmlMapper xmlMapper = new XmlMapper();

    public Optional<WeatherResponse> fetchWeather(String city) {
        try {
            String url = this.buildUrl(city);
            log.info("Consultando clima en XML: {}", url);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                log.warn("Respuesta no exitosa: {}", response.statusCode());
                return Optional.empty();
            }

            String xml = response.body();
            WeatherResponse weather = xmlMapper.readValue(xml.getBytes(StandardCharsets.UTF_8), WeatherResponse.class);
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
        return "http://api.weatherapi.com/v1/current.xml?key=%s&q=%s&aqi=yes".formatted(API_KEY, city);
    }
}

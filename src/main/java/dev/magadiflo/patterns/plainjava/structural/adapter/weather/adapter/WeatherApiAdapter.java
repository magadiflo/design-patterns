package dev.magadiflo.patterns.plainjava.structural.adapter.weather.adapter;

import dev.magadiflo.patterns.plainjava.structural.adapter.weather.adaptee.WeatherApi;
import dev.magadiflo.patterns.plainjava.structural.adapter.weather.adaptee.dto.xml.WeatherResponse;
import dev.magadiflo.patterns.plainjava.structural.adapter.weather.target.WeatherInfo;
import dev.magadiflo.patterns.plainjava.structural.adapter.weather.target.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class WeatherApiAdapter implements WeatherService {

    private final WeatherApi api;

    @Override
    public WeatherInfo getWeather(String city) {
        return this.api.fetchWeather(city)
                .map(this::toWeatherInfo)
                .orElseThrow(() -> new RuntimeException("No se pudo obtener el clima para " + city));
    }

    private WeatherInfo toWeatherInfo(WeatherResponse response) {
        return new WeatherInfo(
                response.location().name(),
                response.location().country(),
                response.current().temperature(),
                response.current().condition().text()
        );
    }
}

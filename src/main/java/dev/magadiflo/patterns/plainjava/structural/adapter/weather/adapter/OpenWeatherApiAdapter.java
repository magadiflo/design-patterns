package dev.magadiflo.patterns.plainjava.structural.adapter.weather.adapter;

import dev.magadiflo.patterns.plainjava.structural.adapter.weather.adaptee.OpenWeatherApi;
import dev.magadiflo.patterns.plainjava.structural.adapter.weather.adaptee.dto.json.OpenWeatherResponse;
import dev.magadiflo.patterns.plainjava.structural.adapter.weather.target.WeatherInfo;
import dev.magadiflo.patterns.plainjava.structural.adapter.weather.target.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class OpenWeatherApiAdapter implements WeatherService {

    private final OpenWeatherApi api;

    @Override
    public WeatherInfo getWeather(String city) {
        return this.api.fetchWeather(city)
                .map(this::toWeatherInfo)
                .orElseThrow(() -> new RuntimeException("No se pudo obtener el clima para " + city));
    }

    private WeatherInfo toWeatherInfo(OpenWeatherResponse response) {
        return new WeatherInfo(
                response.name(),
                response.sys().country(),
                response.main().temp(),
                response.weathers().getFirst().description()
        );
    }
}

package dev.magadiflo.patterns.springboot.structural.adapter.weather.service;

import dev.magadiflo.patterns.springboot.structural.adapter.weather.client.OpenWeatherApiClient;
import dev.magadiflo.patterns.springboot.structural.adapter.weather.dto.WeatherInfo;
import dev.magadiflo.patterns.springboot.structural.adapter.weather.dto.json.OpenWeatherResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class OpenWeatherAdapterService implements WeatherService {

    private final OpenWeatherApiClient api;

    @Override
    public WeatherInfo getWeather(String city) {
        OpenWeatherResponse weatherResponse = this.api.fetchWeather(city);
        return this.toWeatherInfo(weatherResponse);
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

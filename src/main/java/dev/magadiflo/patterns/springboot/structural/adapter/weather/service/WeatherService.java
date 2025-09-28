package dev.magadiflo.patterns.springboot.structural.adapter.weather.service;

import dev.magadiflo.patterns.springboot.structural.adapter.weather.dto.WeatherInfo;

public interface WeatherService {
    WeatherInfo getWeather(String city);
}

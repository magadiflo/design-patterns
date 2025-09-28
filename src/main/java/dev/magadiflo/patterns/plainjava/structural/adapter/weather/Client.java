package dev.magadiflo.patterns.plainjava.structural.adapter.weather;

import dev.magadiflo.patterns.plainjava.structural.adapter.weather.adaptee.OpenWeatherApi;
import dev.magadiflo.patterns.plainjava.structural.adapter.weather.adaptee.WeatherApi;
import dev.magadiflo.patterns.plainjava.structural.adapter.weather.adapter.OpenWeatherApiAdapter;
import dev.magadiflo.patterns.plainjava.structural.adapter.weather.adapter.WeatherApiAdapter;
import dev.magadiflo.patterns.plainjava.structural.adapter.weather.target.WeatherInfo;
import dev.magadiflo.patterns.plainjava.structural.adapter.weather.target.WeatherService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Client {
    public static void main(String[] args) {
        WeatherService weatherService = new WeatherApiAdapter(new WeatherApi());
        WeatherInfo weatherInfo = weatherService.getWeather("Chimbote");
        log.info("{}", weatherInfo);

        WeatherService openWeatherService = new OpenWeatherApiAdapter(new OpenWeatherApi());
        WeatherInfo openWeatherInfo = openWeatherService.getWeather("Chimbote");
        log.info("{}", openWeatherInfo);
    }
}

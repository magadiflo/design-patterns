package dev.magadiflo.patterns.plainjava.structural.adapter.weather.target;

public record WeatherInfo(String city,
                          String country,
                          double temperature,
                          String description) {
}

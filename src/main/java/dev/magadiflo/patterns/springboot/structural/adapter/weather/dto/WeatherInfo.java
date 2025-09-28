package dev.magadiflo.patterns.springboot.structural.adapter.weather.dto;

public record WeatherInfo(String city,
                          String country,
                          double temperature,
                          String description) {
}
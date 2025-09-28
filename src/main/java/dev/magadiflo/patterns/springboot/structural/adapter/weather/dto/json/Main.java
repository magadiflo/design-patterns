package dev.magadiflo.patterns.springboot.structural.adapter.weather.dto.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Main(double temp) {
}

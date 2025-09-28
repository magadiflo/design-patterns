package dev.magadiflo.patterns.plainjava.structural.adapter.weather.adaptee.dto.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Sys(String country) {
}

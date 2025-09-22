package dev.magadiflo.patterns.plainjava.structural.adapter.weather.adaptee.dto.xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Location(String name,
                       String country) {
}

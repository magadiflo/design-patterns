package dev.magadiflo.patterns.springboot.structural.adapter.weather.dto.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenWeatherResponse(String name,
                                  Sys sys,
                                  Main main,
                                  @JsonProperty(value = "weather")
                                  List<Weather> weathers) {
}

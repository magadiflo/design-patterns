package dev.magadiflo.patterns.plainjava.structural.adapter.weather.adaptee.dto.xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Current(@JacksonXmlProperty(localName = "temp_c") double temperature,
                      Condition condition) {
}

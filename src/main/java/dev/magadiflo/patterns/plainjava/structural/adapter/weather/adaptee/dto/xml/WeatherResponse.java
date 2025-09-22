package dev.magadiflo.patterns.plainjava.structural.adapter.weather.adaptee.dto.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "root")
public record WeatherResponse(Location location,
                              Current current) {
}

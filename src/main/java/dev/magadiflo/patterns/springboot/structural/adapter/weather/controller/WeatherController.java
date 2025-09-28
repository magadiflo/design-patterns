package dev.magadiflo.patterns.springboot.structural.adapter.weather.controller;

import dev.magadiflo.patterns.springboot.structural.adapter.weather.dto.WeatherInfo;
import dev.magadiflo.patterns.springboot.structural.adapter.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/weathers")
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping
    public ResponseEntity<WeatherInfo> getWeather(@RequestParam String city) {
        return ResponseEntity.ok(this.weatherService.getWeather(city));
    }

}

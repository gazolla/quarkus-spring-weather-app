package com.gazapps.weatherclient.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;

import com.gazapps.weatherclient.model.WeatherResponse;

import reactor.core.publisher.Mono;

@Controller
public class WeatherController {

    private final WebClient webClient;

    public WeatherController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/weather")
    public Mono<String> getWeather(@RequestParam String location, Model model) {
        return webClient.get()
                .uri("http://localhost:8080/weather/{location}", location)
                .retrieve()
                .bodyToMono(WeatherResponse.class)
                .map(weatherResponse -> {
                    model.addAttribute("weather", weatherResponse);
                    model.addAttribute("location", location);
                    return "weather-partial";
                });
    }
}
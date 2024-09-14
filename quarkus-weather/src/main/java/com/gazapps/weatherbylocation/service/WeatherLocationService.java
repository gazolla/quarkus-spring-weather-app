package com.gazapps.weatherbylocation.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.CompletionStage;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.gazapps.weatherbylocation.model.ResponseGeoLocation;
import com.gazapps.weatherbylocation.model.ResponseWeather;
import com.gazapps.weatherbylocation.model.WeatherResponse;
import com.gazapps.weatherbylocation.util.HttpJsonClient;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class WeatherLocationService {

    @Inject
    HttpJsonClient httpJsonClient;

    @ConfigProperty(name = "geolocation.api.key")
    String geoLocationApiKey;

    @ConfigProperty(name = "weather.api.key")
    String weatherApiKey;

    public CompletionStage<WeatherResponse> getWeatherByLocationAsync(String location) throws UnsupportedEncodingException {
        if (location == null || location.isBlank()) {
            throw new IllegalArgumentException("Location cannot be empty");
        }
        
        String encodedLocation = URLEncoder.encode(location, "UTF-8");
        String limit = "1";
        String urlGeoLocation = String.format("https://api.mapbox.com/geocoding/v5/mapbox.places/%s.json?access_token=%s&limit=%s", encodedLocation, geoLocationApiKey, limit);

        return httpJsonClient.extractDataAsync(urlGeoLocation, ResponseGeoLocation.class)
            .thenCompose(geoLocation -> {
                if (geoLocation == null || geoLocation.features().isEmpty()) {
                    throw new RuntimeException("Location not found");
                }

                double latitude = geoLocation.getLatitude();
                double longitude = geoLocation.getLongitude();
                String urlWeather = String.format("https://api.weatherstack.com/current?access_key=%s&query=%s,%s", weatherApiKey, latitude, longitude);

                return httpJsonClient.extractDataAsync(urlWeather, ResponseWeather.class)
                    .thenApply(weather -> {
                        if (weather == null) {
                            throw new RuntimeException("Unable to fetch weather conditions");
                        }
                        return new WeatherResponse(geoLocation, weather);
                    });
            });
    }
}
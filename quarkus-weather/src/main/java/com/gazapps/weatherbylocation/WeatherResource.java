package com.gazapps.weatherbylocation;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import com.gazapps.weatherbylocation.service.WeatherLocationService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/weather")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WeatherResource {

    @Inject
    WeatherLocationService weatherLocationService;

    @GET
    @Path("/{location}")
    public CompletionStage<Response> getWeather(@PathParam("location") String location) {
        try {
            return weatherLocationService.getWeatherByLocationAsync(location)
                .thenApply(weatherResponse -> Response.ok(weatherResponse).build())
                .exceptionally(throwable -> Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(throwable.getMessage()))
                    .build());
        } catch (IOException e) {
            return CompletableFuture.completedFuture(
                Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build()
            );
        }
    }
}

class ErrorResponse {
    public String error;

    public ErrorResponse(String message) {
        this.error = message;
    }
}
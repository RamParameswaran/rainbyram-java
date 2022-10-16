package dev.findram.services;


import com.google.gson.Gson;
import dev.findram.dtos.ForecastDataPointDTO;
import dev.findram.dtos.HourlyForecastDTO;
import dev.findram.dtos.WeatherForecastDTO;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherService {
    String OwmApiKey = System.getenv("OWM_API_KEY");

    private final URI Url = URI.create("https://api.openweathermap.org/data/2.5/onecall");

    public WeatherForecastDTO getForecastForLatLon(double lat, double lon) throws IOException, InterruptedException {
        URI Uri = UriBuilder.fromUri(Url)
                .queryParam("appid", OwmApiKey)
                .queryParam("exclude", "current,minutely,daily")
                .queryParam("lat", lat)
                .queryParam("lon", lon)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(Uri)
                .GET()
                .build();

        HttpResponse<String> response = HttpClient.newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        return gson.fromJson(response.body(), WeatherForecastDTO.class);
    }


    /**
     * Checks if rain is forecast in the upcoming `hours_to_look_ahead` hourly weather data points.
     * Rain is indicated by `WeatherData.id` values < 700.
     *
     * @return `true` if the forecast indicates rain in the following `hours_to_look_ahead` hours.
     */
    public boolean checkForRainInNextNHours (WeatherForecastDTO forecast, int hours_to_look_ahead) {
        for(int index = 0; index <= hours_to_look_ahead; index++){

            var hourlyForecast = (HourlyForecastDTO)Array.get(forecast.hourly, index);
            for (ForecastDataPointDTO weatherData : hourlyForecast.getWeather()) {
                if (weatherData.getId() < 700) {
                    return true;
                }
            }
        }

        return false;
    }

}

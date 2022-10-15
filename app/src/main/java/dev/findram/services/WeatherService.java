package dev.findram.services;


import com.google.gson.Gson;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherService {
    String OwmApiKey = System.getenv("OWM_API_KEY");

    private URI Url = URI.create("https://api.openweathermap.org/data/2.5/onecall");
    private HttpClient client = HttpClient.newHttpClient();

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
        WeatherForecastDTO forecast = gson.fromJson(response.body(), WeatherForecastDTO.class);

        return forecast;
    };


    /**
     * Checks if rain is forecast in the upcoming `hours_to_look_ahead` hourly weather data points.
     * Rain is indicated by `WeatherData.id` values < 700.
     *
     * @return `true` if the forecast indicates rain in the following `hours_to_look_ahead` hours.
     */
    public boolean checkForRainInNextNHours (WeatherForecastDTO forecast, int hours_to_look_ahead) {
        int counter = 0;

        for(int index = 0; index <= hours_to_look_ahead; index++){

            var hourlyForecast = (WeatherForecastDTO.HourlyForecast)Array.get(forecast.hourly, index);
            for (WeatherForecastDTO.WeatherData weatherData : hourlyForecast.weather) {
                if (weatherData.id < 700) {
                    return true;
                }
            }
        }

        return false;
    };

}

package dev.findram.services;

import dev.findram.entities.ForecastDataPoint;
import dev.findram.entities.HourlyForecast;
import dev.findram.entities.WeatherForecast;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.doReturn;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class WeatherServiceTest {

    WeatherService spyWeatherService = Mockito.spy(new WeatherService());

    ForecastDataPoint[] weather_no_rain = new ForecastDataPoint[] {
            ForecastDataPoint
            .builder()
            .id(800)
            .build()
            };
    ForecastDataPoint[] weather_rain = new ForecastDataPoint[]{
            ForecastDataPoint
            .builder()
            .id(699)
            .build()
            };


    @Test
    public void testCheckForRainReturnsFalseWhenRainNotInUpcomingForecast()  throws IOException, InterruptedException {

        WeatherForecast mockWeatherForecast = WeatherForecast
                .builder()
                .lon(123)
                .lat(789)
                .hourly(new HourlyForecast[]{
                                HourlyForecast
                                    .builder()
                                    .weather(weather_no_rain)
                                    .build(),
                                HourlyForecast
                                    .builder()
                                    .weather(weather_no_rain)
                                    .build(),
                                HourlyForecast
                                    .builder()
                                    .weather(weather_no_rain)
                                    .build(),
                                HourlyForecast
                                    .builder()
                                    .weather(weather_no_rain)
                                    .build()
                        }
                )
                .build();

        // Mock the http call and return a mocked WeatherForecastDTO object
        doReturn(mockWeatherForecast).when(spyWeatherService).getForecastForLatLon(anyDouble(), anyDouble());

        assertFalse(spyWeatherService.checkForRainInNextNHours(mockWeatherForecast, 3));

    }


    @Test
    public void testCheckForRainReturnsTrueWhenRainInUpcomingForecast()  throws IOException, InterruptedException{

        WeatherForecast mockWeatherForecast = WeatherForecast
                .builder()
                .lon(123)
                .lat(789)
                .hourly(new HourlyForecast[]{
                                HourlyForecast
                                    .builder()
                                    .weather(weather_no_rain)
                                    .build(),
                                HourlyForecast
                                    .builder()
                                    .weather(weather_rain)
                                    .build(),
                                HourlyForecast
                                    .builder()
                                    .weather(weather_no_rain)
                                    .build(),
                                HourlyForecast
                                    .builder()
                                    .weather(weather_no_rain)
                                    .build()
                        }
                )
                .build();

        // Mock the http call and return a mocked WeatherForecastDTO object
        doReturn(mockWeatherForecast).when(spyWeatherService).getForecastForLatLon(anyDouble(), anyDouble());

        assertTrue(spyWeatherService.checkForRainInNextNHours(mockWeatherForecast, 3));

    }

}

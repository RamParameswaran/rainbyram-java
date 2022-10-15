package dev.findram.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.doReturn;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class WeatherServiceTest {

    WeatherService spyWeatherService = Mockito.spy(new WeatherService());

    WeatherForecastDTO.WeatherData[] weather_no_rain = new WeatherForecastDTO.WeatherData[] {
            WeatherForecastDTO.WeatherData
            .builder()
            .id(800)
            .build()
            };
    WeatherForecastDTO.WeatherData[] weather_rain = new WeatherForecastDTO.WeatherData[]{
            WeatherForecastDTO.WeatherData
            .builder()
            .id(699)
            .build()
            };


    @Test
    public void testCheckForRainReturnsFalseWhenRainNotInUpcomingForecast()  throws IOException, InterruptedException {

        WeatherForecastDTO mockWeatherForecast = WeatherForecastDTO
                .builder()
                .lon(123)
                .lat(789)
                .hourly(new WeatherForecastDTO.HourlyForecast[]{
                                WeatherForecastDTO.HourlyForecast
                                    .builder()
                                    .weather(weather_no_rain)
                                    .build(),
                                WeatherForecastDTO.HourlyForecast
                                    .builder()
                                    .weather(weather_no_rain)
                                    .build(),
                                WeatherForecastDTO.HourlyForecast
                                    .builder()
                                    .weather(weather_no_rain)
                                    .build(),
                                WeatherForecastDTO.HourlyForecast
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

        WeatherForecastDTO mockWeatherForecast = WeatherForecastDTO
                .builder()
                .lon(123)
                .lat(789)
                .hourly(new WeatherForecastDTO.HourlyForecast[]{
                                WeatherForecastDTO.HourlyForecast
                                        .builder()
                                        .weather(weather_no_rain)
                                        .build(),
                                WeatherForecastDTO.HourlyForecast
                                        .builder()
                                        .weather(weather_rain)
                                        .build(),
                                WeatherForecastDTO.HourlyForecast
                                        .builder()
                                        .weather(weather_no_rain)
                                        .build(),
                                WeatherForecastDTO.HourlyForecast
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

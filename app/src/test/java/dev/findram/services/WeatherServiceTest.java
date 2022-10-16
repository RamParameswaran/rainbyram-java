package dev.findram.services;

import dev.findram.dtos.ForecastDataPointDTO;
import dev.findram.dtos.HourlyForecastDTO;
import dev.findram.dtos.WeatherForecastDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.doReturn;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class WeatherServiceTest {

    WeatherService spyWeatherService = Mockito.spy(new WeatherService());

    ForecastDataPointDTO[] weather_no_rain = new ForecastDataPointDTO[] {
            ForecastDataPointDTO
            .builder()
            .id(800)
            .build()
            };
    ForecastDataPointDTO[] weather_rain = new ForecastDataPointDTO[]{
            ForecastDataPointDTO
            .builder()
            .id(699)
            .build()
            };


    @Test
    public void testCheckForRainReturnsFalseWhenRainNotInUpcomingForecast()  throws IOException, InterruptedException {

        WeatherForecastDTO mockWeatherForecastDTO = WeatherForecastDTO
                .builder()
                .lon(123)
                .lat(789)
                .hourly(new HourlyForecastDTO[]{
                                HourlyForecastDTO
                                    .builder()
                                    .weather(weather_no_rain)
                                    .build(),
                                HourlyForecastDTO
                                    .builder()
                                    .weather(weather_no_rain)
                                    .build(),
                                HourlyForecastDTO
                                    .builder()
                                    .weather(weather_no_rain)
                                    .build(),
                                HourlyForecastDTO
                                    .builder()
                                    .weather(weather_no_rain)
                                    .build()
                        }
                )
                .build();

        // Mock the http call and return a mocked WeatherForecastDTO object
        doReturn(mockWeatherForecastDTO).when(spyWeatherService).getForecastForLatLon(anyDouble(), anyDouble());

        assertFalse(spyWeatherService.checkForRainInNextNHours(mockWeatherForecastDTO, 3));

    }


    @Test
    public void testCheckForRainReturnsTrueWhenRainInUpcomingForecast()  throws IOException, InterruptedException{

        WeatherForecastDTO mockWeatherForecastDTO = WeatherForecastDTO
                .builder()
                .lon(123)
                .lat(789)
                .hourly(new HourlyForecastDTO[]{
                                HourlyForecastDTO
                                    .builder()
                                    .weather(weather_no_rain)
                                    .build(),
                                HourlyForecastDTO
                                    .builder()
                                    .weather(weather_rain)
                                    .build(),
                                HourlyForecastDTO
                                    .builder()
                                    .weather(weather_no_rain)
                                    .build(),
                                HourlyForecastDTO
                                    .builder()
                                    .weather(weather_no_rain)
                                    .build()
                        }
                )
                .build();

        // Mock the http call and return a mocked WeatherForecastDTO object
        doReturn(mockWeatherForecastDTO).when(spyWeatherService).getForecastForLatLon(anyDouble(), anyDouble());

        assertTrue(spyWeatherService.checkForRainInNextNHours(mockWeatherForecastDTO, 3));

    }

}

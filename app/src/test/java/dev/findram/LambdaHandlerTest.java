/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package dev.findram;

import dev.findram.dtos.HourlyForecastDTO;
import dev.findram.helpers.TestContext;
import dev.findram.dtos.WeatherForecastDTO;
import dev.findram.services.SnsService;
import dev.findram.services.WeatherService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Map;

import static java.util.Map.entry;
import static org.mockito.ArgumentMatchers.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LambdaHandlerTest {
    WeatherService spyWeatherService = Mockito.spy(new WeatherService());
    SnsService spySnsService = Mockito.spy(new SnsService());

    @BeforeAll
    void setUp() throws IOException, InterruptedException {
        var mockReturn = new WeatherForecastDTO(
                123,
                123,
                "gmt",
                new HourlyForecastDTO[]{});

        Mockito.doReturn(mockReturn).when(spyWeatherService).getForecastForLatLon(anyDouble(), anyDouble());
        Mockito.doNothing().when(spySnsService).publish(anyString());
    }

    @Test void handlerExecutesWithoutError_NoRainForecasted() {
        Mockito.doReturn(false).when(spyWeatherService).checkForRainInNextNHours(any(), anyInt());

        LambdaHandler lambdaHandler = new LambdaHandler(spyWeatherService, spySnsService);

        var response = lambdaHandler.handleRequest(
                Map.ofEntries(
                        entry("foo","bar")
                ),
                new TestContext()
        );

        Assertions.assertTrue(response.contains("\"status\": 200, \"body\": \"No rain forecast. No action required.\""));
    }

    @Test void handlerExecutesWithoutError_RainForecasted() {
        Mockito.doReturn(true).when(spyWeatherService).checkForRainInNextNHours(any(), anyInt());

        LambdaHandler lambdaHandler = new LambdaHandler(spyWeatherService, spySnsService);

        var response = lambdaHandler.handleRequest(
                Map.ofEntries(
                        entry("foo","bar")
                ),
                new TestContext()
        );

        Assertions.assertTrue(response.contains("\"status\": 200, \"body\": \"Success. Text message sent to all subscribers via SMS.\""));
    }
}

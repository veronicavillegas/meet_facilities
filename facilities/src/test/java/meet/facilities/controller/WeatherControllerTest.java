package meet.facilities.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import meet.facilities.dto.Weather;
import meet.facilities.exception.ApiException;
import meet.facilities.exception.InvalidInputDataException;
import meet.facilities.exception.NotFoundWeatherException;
import meet.facilities.service.weather.WeatherService;

@RunWith(MockitoJUnitRunner.class)
public class WeatherControllerTest {
    @InjectMocks
    WeatherController weatherController;
    @Mock
    WeatherService weatherService;
    @Spy
    ModelMapper modelMapper;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getWeather_Ok()
            throws IOException, NotFoundWeatherException, InvalidInputDataException  {
        String country = "ar";
        String city = "Mendoza";
        String date = "2020-01-30 08:00:00";
        Weather weather = getWeather();

        when(weatherService.getWeather(country, city, date)).thenReturn(weather);

        ResponseEntity<meet.facilities.model.Weather> response = weatherController.getWeather(country, city, date);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(weather.getMaxTemp(), response.getBody().getMaxTemp());
        assertEquals(weather.getMinTemp(), response.getBody().getMinTemp());
    }

    @Test
    public void getWeather_NotFoundWeatherException()
            throws IOException, NotFoundWeatherException, InvalidInputDataException  {
        String country = "ar";
        String city = "Mendoza";
        String date = "2020-01-30 08:00:00";
        String message = "Weather not found";

        when(weatherService.getWeather(country, city, date)).thenThrow(new NotFoundWeatherException(message));

        try {
            weatherController.getWeather(country, city, date);
            fail("Exception expected but not was thrown");
        } catch (ApiException ex){
            assertEquals(HttpStatus.BAD_REQUEST.value(), (int)ex.getStatusCode());
            assertEquals(message, ex.getDescription());
        }
    }

    @Test(expected = ApiException.class)
    public void getWeather_IOException()
            throws IOException, NotFoundWeatherException, InvalidInputDataException  {
        String country = "ar";
        String city = "Mendoza";
        String date = "2020-01-30 08:00:00";

        when(weatherService.getWeather(country, city, date)).thenThrow(new IOException());

        weatherController.getWeather(country, city, date);
    }

    private Weather getWeather() {
        Weather weather = new Weather();
        weather.setMaxTemp(30);
        weather.setMinTemp(25);
        return weather;
    }
}

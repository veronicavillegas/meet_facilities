package meet.facilities.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import meet.facilities.dto.Location;
import meet.facilities.exception.ApiException;
import meet.facilities.exception.InvalidInputDataException;
import meet.facilities.exception.NotFoundWeatherException;
import meet.facilities.service.weather.WeatherService;

@RestController
@RequestMapping("/meetup")
public class WeatherController {
    WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather")
    public void getWeather(@RequestParam String country, @RequestParam String city, @RequestParam String date)
    {
        try {
            Location location = getLocation(city, country);
            Date forestDate = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").parse(date);
            weatherService.getWeather(forestDate, location);
        } catch(IOException | ParseException ex) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        } catch(NotFoundWeatherException | InvalidInputDataException ex) {
            throw new ApiException(HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        }
    }

    private Location getLocation(String city, String country) {
        Location location = new Location();
        location.setCity(city);
        location.setCountry(country);

        return location;
    }
}

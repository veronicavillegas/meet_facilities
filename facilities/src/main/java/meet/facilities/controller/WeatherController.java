package meet.facilities.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import meet.facilities.dto.Location;
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
            throws IOException, ParseException {
        Location location = new Location();
        location.setCity(city);
        location.setCountry(country);
        Date forestDate = new SimpleDateFormat("yyyy-mm-dd").parse(date);

        weatherService.getWeather(forestDate, location);
    }
}

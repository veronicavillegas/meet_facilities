package meet.facilities.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException.NotImplemented;

@RestController
@RequestMapping("/meetup")
public class WeatherController {
    @Autowired
    public WeatherController() {

    }

    @GetMapping
    public void calculateBeerBox() {
    }
}

package meet.facilities.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import meet.facilities.client.WeatherBitClient;
import meet.facilities.dto.Location;
import meet.facilities.dto.Meet;
import meet.facilities.dto.User;
import meet.facilities.exception.ApiException;
import meet.facilities.exception.InvalidInputDataException;
import meet.facilities.exception.NotFoundWeatherException;
import meet.facilities.model.Beer;
import meet.facilities.service.FoodService;

@RestController
@RequestMapping("/meetup")
public class BeerController {
    FoodService foodService;
    ModelMapper modelMapper;

    @Autowired
    public BeerController(FoodService foodService, ModelMapper modelMapper) {
        this.foodService = foodService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/beers")
    public ResponseEntity<Beer> calculateBeer(@RequestParam String emailUser, @RequestParam String date,
            @RequestParam int attendants, @RequestParam String city, @RequestParam String country,
            @RequestParam int beersByBox) {
        // Creo dto de user, meet, y whether
        // Llamo al servicio para calcular la cantidad de cervezas
        User user = getUser(emailUser);
        meet.facilities.dto.Beer beer;

        try {
            Meet meet = getMeet(date, attendants, city, country);
            beer = foodService.calculateBeer(meet, user, beersByBox, attendants);
        } catch (IOException ex) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value());
        } catch (NotFoundWeatherException | InvalidInputDataException | ParseException ex) {
            throw new ApiException(HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage(),
                    HttpStatus.BAD_REQUEST.value());
        }

        return new ResponseEntity<>(modelMapper.map(beer, Beer.class), HttpStatus.OK);
    }

    private User getUser(String emailUser) {
        User user = new User();
        user.setEmail(emailUser);

        return user;
    }

    private Meet getMeet(String date, int attendants, String city, String country) throws ParseException {
        Location location = getLocation(city, country);
        Date forestDate = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").parse(date);
        
        Meet meet = new Meet();
        meet.setLocation(location);
        meet.setDate(forestDate);

        return meet;
    }

    private Location getLocation(String city,
    String country) {
        Location location = new Location();
        location.setCity(city);
        location.setCountry(country);

        return location;
    }
}

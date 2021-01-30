package meet.facilities.service.beer;

import java.io.IOException;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver;

import meet.facilities.dto.Beer;
import meet.facilities.dto.Location;
import meet.facilities.dto.Meet;
import meet.facilities.dto.User;
import meet.facilities.dto.Weather;
import meet.facilities.exception.InvalidInputDataException;
import meet.facilities.exception.NotFoundWeatherException;
import meet.facilities.service.weather.WeatherService;
import meet.facilities.util.Constant;
import meet.facilities.util.Helper;
import meet.facilities.validator.MeetDataValidator;

@Service
public class BeerService {
    private BeerCalculator beerCalculator;
    private MeetDataValidator meetDataValidator;

    @Autowired
    public BeerService(BeerCalculator beerCalculator, MeetDataValidator meetDataValidator) {
        this.beerCalculator = beerCalculator;
        this.meetDataValidator = meetDataValidator;
    }

    public Beer calculateBeer(String emailUser, String givenDate, int attendants, String city, String country, int beersByBox)
            throws IOException, InvalidInputDataException, NotFoundWeatherException  {
        
        Date date = Helper.parseToDate(givenDate);
        Location location = getLocation(city, country);

        validateData(emailUser, date, attendants, location, beersByBox);
        
        Meet meet = getMeet(date, attendants, location);

        int boxes = beerCalculator.calculateBoxesOfBeers(meet, beersByBox, attendants);
        
        Beer beer = getBeer(boxes);

        return beer;
    }

    private void validateData(String emailUser, Date date, int attendants, Location location, int beersByBox) throws InvalidInputDataException {
        meetDataValidator.validateUser(emailUser);
        meetDataValidator.validateDate(date);
        meetDataValidator.validateBeersByBox(beersByBox);
        meetDataValidator.validateAttendants(attendants);
        meetDataValidator.validateLocation(location);
    }

    private Beer getBeer(int boxes) {
        Beer beer = new Beer();
        beer.setAmountOfBoxes(boxes);
        return beer;
    }

    private Meet getMeet(Date date, int attendants, Location location)  {        
        Meet meet = new Meet();
        meet.setLocation(location);
        meet.setDate(date);

        return meet;
    }

    private Location getLocation(String city, String country) {
        Location location = new Location();
        location.setCity(city);
        location.setCountry(country);

        return location;
    }
}

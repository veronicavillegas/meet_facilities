package meet.facilities.service.beer;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver;

import meet.facilities.dto.Beer;
import meet.facilities.dto.Meet;
import meet.facilities.dto.User;
import meet.facilities.dto.Weather;
import meet.facilities.exception.InvalidInputDataException;
import meet.facilities.exception.NotFoundWeatherException;
import meet.facilities.service.weather.WeatherService;
import meet.facilities.util.Constant;
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

    public Beer calculateBeer(Meet meet, User user, int beersByBox, int attendants)
            throws IOException, InvalidInputDataException, NotFoundWeatherException {
        validateData(meet, user, beersByBox, attendants);
        int boxes = beerCalculator.calculateBoxesOfBeers(meet, beersByBox, attendants);
        Beer beer = getBeer(boxes);

        return beer;
    }

    private void validateData(Meet meet, User user, int beersByBox, int attendants) throws InvalidInputDataException {
        meetDataValidator.validateMeet(meet);
        meetDataValidator.validateUser(user);
        meetDataValidator.validateBeersByBox(beersByBox);
        meetDataValidator.validateAttendants(attendants);
    }

    public Beer getBeer(int boxes) {
        Beer beer = new Beer();
        beer.setAmountOfBoxes(boxes);
        return beer;
    }
}

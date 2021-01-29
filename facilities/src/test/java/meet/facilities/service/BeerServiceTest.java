package meet.facilities.service;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import meet.facilities.dto.Beer;
import meet.facilities.dto.Location;
import meet.facilities.dto.Meet;
import meet.facilities.dto.User;
import meet.facilities.dto.Weather;
import meet.facilities.exception.InvalidInputDataException;
import meet.facilities.exception.NotFoundWeatherException;
import meet.facilities.service.beer.BeerCalculator;
import meet.facilities.service.beer.BeerService;
import meet.facilities.service.weather.WeatherService;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

@RunWith(MockitoJUnitRunner.class)
public class BeerServiceTest {
    @InjectMocks
    BeerService beerService;

    @Mock
    WeatherService weatherService;
    @Mock
    BeerCalculator beerCalculator;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void calculateBeersOK() throws IOException, NotFoundWeatherException, InvalidInputDataException {
        Weather weather = new Weather();
        Meet meet = getMeet();
        User user = getUser();
        int expectedBoxes = 5;
        int attendants = 10;
        int beersByBox = 6;
        when(weatherService.getWeather(any(), any())).
                thenReturn(weather);
        when(beerCalculator.calculateBoxesOfBeers(any(Meet.class), anyInt(), anyInt())).
                thenReturn(expectedBoxes);

        Beer beer = beerService.calculateBeer(meet, user, beersByBox, attendants);

        assertEquals(expectedBoxes, beer.getAmountOfBoxes());
    }

    @Test
    public void calculateBeers_InvalidMeet() {
        // TODO:
    }

    @Test
    public void calculateBeers_InvalidUser() {
        // TODO:
    }

    @Test
    public void calculateBeers_InvalidCountOfAttendants() {
        // TODO:
    }

    @Test
    public void calculateBeers_InvalidCountOfBeersInBox() {
        // TODO:
    }

    private User getUser() {
        User user = new User();
        user.setEmail("vero@gmail.com");
        return user;
    }

    private Meet getMeet() {
        Location location = new Location();
        location.setCity("Mendoza");
        location.setCountry("Argentina");

        Meet meet = new Meet();
        meet.setDate(new Date());
        meet.setLocation(location);
        return meet;
    }
}

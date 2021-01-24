package meet.facilities.service;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import meet.facilities.dto.Beer;
import meet.facilities.dto.Meet;
import meet.facilities.dto.User;
import meet.facilities.dto.Weather;
import meet.facilities.service.weather.WeatherService;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
    public void calculateBeersOK() {
        Meet meet = getMeet();
        User user = getUser();
        int expectedBoxes = 5;
        int attendants = 10;
        int beersByBox = 6;
        when(weatherService.getWeather(meet.getDate(), meet.getLocation())).
                thenReturn(any());
        when(beerCalculator.calculateBoxesOfBeers(any(), attendants, beersByBox)).
                thenReturn(expectedBoxes);

        Beer beer = beerService.calculateBeer(meet, user, beersByBox, attendants);

        assertEquals(expectedBoxes, beer.getAmountOfBoxes());
    }

    private User getUser() {
        return null;
    }

    private Meet getMeet() {
        return null;
    }

    private Weather getWeather(int maxTemp) {
        Weather weather = new Weather();
        weather.setMaxTemp(maxTemp);
        return weather;
    }
}

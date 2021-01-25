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
import meet.facilities.service.beer.BeerCalculator;
import meet.facilities.service.weather.WeatherService;
import meet.facilities.util.Constant;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

@RunWith(MockitoJUnitRunner.class)
public class BeerCalculatorServiceTest {
    @InjectMocks
    BeerCalculator beerCalculator;

    @Mock
    WeatherService weatherService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void calculateBeers_coldDay() {
        Meet meet = getMeet();
        int beersByBox = 6;
        int attendants = 10;
        int temp = 19;
        Weather weather = getWeather(temp);

        when(weatherService.getWeather(meet.getDate(), meet.getLocation())).thenReturn(weather);
        
        int actualBoxes = beerCalculator.calculateBoxesOfBeers(meet, beersByBox, attendants);
        int expectedBoxes = (int)Math.ceil((attendants * Constant.BEERS_COLD_DAY) / beersByBox);
        
        assertEquals(expectedBoxes, actualBoxes);
    }

    @Test
    public void calculateBeers_hotDay() {
        Meet meet = getMeet();
        int beersByBox = 6;
        int attendants = 10;
        int temp = 30;
        Weather weather = getWeather(temp);

        when(weatherService.getWeather(meet.getDate(), meet.getLocation())).thenReturn(weather);
        
        int actualBoxes = beerCalculator.calculateBoxesOfBeers(meet, beersByBox, attendants);
        int expectedBoxes = (int)Math.ceil((attendants * Constant.BEERS_HOT_DAY) / beersByBox);
        
        assertEquals(expectedBoxes, actualBoxes);
    }

    @Test
    public void calculateBeers_warmDay() {
        Meet meet = getMeet();
        int beersByBox = 6;
        int attendants = 10;
        int temp = 21;
        Weather weather = getWeather(temp);

        when(weatherService.getWeather(meet.getDate(), meet.getLocation())).thenReturn(weather);
        
        int actualBoxes = beerCalculator.calculateBoxesOfBeers(meet, beersByBox, attendants);
        double boxes = (double)(attendants * Constant.BEERS_WARM_DAY) / beersByBox;
        int expectedBoxes = (int)Math.ceil(boxes);
        
        assertEquals(expectedBoxes, actualBoxes);
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

    private Weather getWeather(int temp) {
        Weather weather = new Weather();
        weather.setMaxTemp(temp);
        return weather;
    }
}

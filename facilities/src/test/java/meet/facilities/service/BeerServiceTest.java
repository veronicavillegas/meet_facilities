package meet.facilities.service;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
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
import meet.facilities.util.Constant;
import meet.facilities.validator.MeetDataValidator;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@RunWith(MockitoJUnitRunner.class)
public class BeerServiceTest {
    @InjectMocks
    BeerService beerService;

    @Mock
    WeatherService weatherService;
    @Mock
    BeerCalculator beerCalculator;
    @Spy
    MeetDataValidator meetDataValidator;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void calculateBeersOK()
            throws IOException, NotFoundWeatherException, InvalidInputDataException  {
        String emailUser = "vero@tets.com";
        String date = getValidDate();
        String city = "mendoza";
        String country = "ar";
        int expectedBoxes = 5;
        int attendants = 10;
        int beersByBox = 6;
        
        when(beerCalculator.calculateBoxesOfBeers(any(Meet.class), anyInt(), anyInt())).
                thenReturn(expectedBoxes);

        Beer beer = beerService.calculateBeer(emailUser, date, attendants, city, country, beersByBox);

        assertEquals(expectedBoxes, beer.getAmountOfBoxes());
    }
    
    @Test
    public void calculateBeers_invalidEmail() {
        String emailUser = "vero@tets.com"; 
        String date = getValidDate();
        int attendants = 2;
        String city = "mendoza";
        String country = "ar"; 
        int beersByBox = 6; 
        String expectedMessage = Constant.INVALID_USER_EMAIL_MESSAGE;

        try {
            beerService.calculateBeer(emailUser, date, attendants, city, country, beersByBox);
        } catch (Exception ex) {
            assertEquals(expectedMessage, ex.getMessage());
            assertEquals(ex.getClass(), InvalidInputDataException.class);
        }
    }

    @Test
    public void calculateBeers_invalidDate() {
        String emailUser = "vero@tets.com"; 
        String date = getValidDate();        int attendants = 2;
        String city = "mendoza";
        String country = "ar"; 
        int beersByBox = 6; 
        String expectedMessage = Constant.INVALID_DATE_MESSAGE;

        try {
            beerService.calculateBeer(emailUser, date, attendants, city, country, beersByBox);
        } catch (Exception ex) {
            assertEquals(expectedMessage, ex.getMessage());
            assertEquals(ex.getClass(), InvalidInputDataException.class);
        }
    }

    @Test
    public void calculateBeers_invalidAttendants() {
        String emailUser = "vero@tets.com"; 
        String date = getValidDate();
        int attendants = 1;
        String city = "mendoza";
        String country = "ar"; 
        int beersByBox = 6; 
        String expectedMessage = Constant.INVALID_ATTENDANTS_MESSAGE;

        try {
            beerService.calculateBeer(emailUser, date, attendants, city, country, beersByBox);
        } catch (Exception ex) {
            assertEquals(expectedMessage, ex.getMessage());
            assertEquals(ex.getClass(), InvalidInputDataException.class);
        }
    }

    @Test
    public void calculateBeers_invalidCity() {
        String emailUser = "vero@tets.com"; 
        String date = getValidDate();        int attendants = 2;
        String city = "";
        String country = "ar"; 
        int beersByBox = 6; 
        String expectedMessage = Constant.INVALID_CITY_MESSAGE;

        try {
            beerService.calculateBeer(emailUser, date, attendants, city, country, beersByBox);
        } catch (Exception ex) {
            assertEquals(expectedMessage, ex.getMessage());
            assertEquals(ex.getClass(), InvalidInputDataException.class);
        }
    }

    @Test
    public void calculateBeers_invalidCountry() {
        String emailUser = "vero@tets.com"; 
        String date = getValidDate();
        int attendants = 2;
        String city = "mendoza";
        String country = ""; 
        int beersByBox = 6; 
        String expectedMessage = Constant.INVALID_COUNTRY_MESSAGE;

        try {
            beerService.calculateBeer(emailUser, date, attendants, city, country, beersByBox);
        } catch (Exception ex) {
            assertEquals(expectedMessage, ex.getMessage());
            assertEquals(ex.getClass(), InvalidInputDataException.class);
        }
    }

    @Test
    public void calculateBeers_invalidBeersByBox() {
        String emailUser = "vero@tets.com"; 
        String date = getValidDate();

        int attendants = 2;
        String city = "mendoza";
        String country = "ar"; 
        int beersByBox = 0; 
        String expectedMessage = Constant.INVALID_BEERS_BY_BOX_MESSAGE;

        try {
            beerService.calculateBeer(emailUser, date, attendants, city, country, beersByBox);
        } catch (Exception ex) {
            assertEquals(expectedMessage, ex.getMessage());
            assertEquals(ex.getClass(), InvalidInputDataException.class);
        }
    }

    private String getValidDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().plusDays(1).format(formatter);
    }

    
}

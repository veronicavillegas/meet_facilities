package meet.facilities.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import meet.facilities.dto.Meet;
import meet.facilities.exception.ApiException;
import meet.facilities.exception.InvalidInputDataException;
import meet.facilities.exception.NotFoundWeatherException;
import meet.facilities.model.Beer;
import meet.facilities.service.FoodService;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.text.ParseException;

@RunWith(MockitoJUnitRunner.class)
public class BeerControllerTest {
    @InjectMocks
    BeerController beerController;
    @Mock
    FoodService foodService;
    @Spy
    ModelMapper modelMapper;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void calculateBeer_ok()
            throws IOException, InvalidInputDataException, NotFoundWeatherException, ParseException {
        String emailUser = "vero@test.com";
        String date = "2020-01-20 08:05:05";
        int attendants = 1;
        String city = "Mendoza";
        String country = "ar";
        int beersByBox = 6;
        int amountOfBoxesExpected = 6;

        meet.facilities.dto.Beer beer = new meet.facilities.dto.Beer();
        beer.setAmountOfBoxes(amountOfBoxesExpected);

        when(foodService.calculateBeer(emailUser, date, attendants, city, country, beersByBox)).thenReturn(beer);
        ResponseEntity<Beer> response = beerController.calculateBeer(emailUser, date, attendants, city, country, beersByBox);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(amountOfBoxesExpected, response.getBody().getAmountOfBoxes());
    }

    @Test
    public void calculateBeer_inputException() 
        throws IOException, NotFoundWeatherException, ParseException, InvalidInputDataException {
            String exceptionMessage = "invalid input";

            when(foodService.calculateBeer(anyString(), anyString(), anyInt(), anyString(), anyString(), anyInt())).
                thenThrow(new InvalidInputDataException(exceptionMessage));
            
            try {
                beerController.calculateBeer(anyString(), anyString(), anyInt(), anyString(), anyString(), anyInt());
            } catch(ApiException ex) {
                assertEquals(exceptionMessage, ex.getDescription());
                assertEquals(HttpStatus.BAD_REQUEST.value(), (int)ex.getStatusCode());
            }
    }

    @Test(expected = ApiException.class)
    public void calculateBeer_IOException() 
        throws IOException, NotFoundWeatherException, ParseException, InvalidInputDataException {

            when(foodService.calculateBeer(anyString(), anyString(), anyInt(), anyString(), anyString(), anyInt())).
                thenThrow(new IOException());
            
            beerController.calculateBeer(anyString(), anyString(), anyInt(), anyString(), anyString(), anyInt());
    }

    @Test(expected = ApiException.class)
    public void calculateBeer_NotFoundWeatherException() 
        throws IOException, NotFoundWeatherException, ParseException, InvalidInputDataException {

            when(foodService.calculateBeer(anyString(), anyString(), anyInt(), anyString(), anyString(), anyInt())).
                thenThrow(new NotFoundWeatherException(""));
            
            beerController.calculateBeer(anyString(), anyString(), anyInt(), anyString(), anyString(), anyInt());
    }

    @Test(expected = ApiException.class)
    public void calculateBeer_ParseException() 
        throws IOException, NotFoundWeatherException, ParseException, InvalidInputDataException {

            when(foodService.calculateBeer(anyString(), anyString(), anyInt(), anyString(), anyString(), anyInt())).
                thenThrow(new ParseException("",0));
            
            beerController.calculateBeer(anyString(), anyString(), anyInt(), anyString(), anyString(), anyInt());
    }
}

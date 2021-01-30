package meet.facilities.service;

import java.io.IOException;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import meet.facilities.dto.Beer;
import meet.facilities.dto.Location;
import meet.facilities.dto.Meet;
import meet.facilities.dto.User;
import meet.facilities.exception.InvalidInputDataException;
import meet.facilities.exception.NotFoundWeatherException;
import meet.facilities.service.beer.BeerService;
import meet.facilities.util.Constant;

@Service
public class FoodService {
	BeerService beerService;

	@Autowired
	public FoodService(BeerService beerService) {
		this.beerService = beerService;
	}

	public Beer calculateBeer(String emailUser, String date, int attendants, String city, String country, int beersByBox)
			throws IOException, InvalidInputDataException, NotFoundWeatherException {
		return beerService.calculateBeer(emailUser, date, attendants, city, country, beersByBox);
	}
  
}

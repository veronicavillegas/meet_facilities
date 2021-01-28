package meet.facilities.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import meet.facilities.dto.Beer;
import meet.facilities.dto.Meet;
import meet.facilities.dto.User;
import meet.facilities.service.beer.BeerService;

@Service
public class FoodService {
	BeerService beerService;

	@Autowired
	public FoodService(BeerService beerService) {
		this.beerService = beerService;
	}

	public Beer calculateBeer(Meet meet, User user, int beersByBox, int attendants) throws IOException {
		//Calcular las cajas de cerveza
		return beerService.calculateBeer(meet, user, beersByBox, attendants);
	}
    
}

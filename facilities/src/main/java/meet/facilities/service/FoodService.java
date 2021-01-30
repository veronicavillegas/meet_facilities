package meet.facilities.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

@Service
public class FoodService {
	BeerService beerService;

	@Autowired
	public FoodService(BeerService beerService) {
		this.beerService = beerService;
	}

	public Beer calculateBeer(String emailUser, String date, int attendants, String city, String country, int beersByBox)
			throws IOException, InvalidInputDataException, NotFoundWeatherException, ParseException {
		User user = getUser(emailUser);
		Meet meet = getMeet(date, attendants, city, country);
		return beerService.calculateBeer(meet, user, beersByBox, attendants);
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

    private Location getLocation(String city, String country) {
        Location location = new Location();
        location.setCity(city);
        location.setCountry(country);

        return location;
    }
    
}

package meet.facilities.service.beer;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver;

import meet.facilities.dto.Beer;
import meet.facilities.dto.Meet;
import meet.facilities.dto.User;
import meet.facilities.dto.Weather;
import meet.facilities.service.weather.WeatherService;
import meet.facilities.util.Constant;

@Service
public class BeerService {
    private BeerCalculator beerCalculator;

    @Autowired
    public BeerService(BeerCalculator beerCalculator) {
        this.beerCalculator = beerCalculator;
    }

    public Beer calculateBeer(Meet meet, User user, int beersByBox, int attendants) throws IOException {
        //TODO: Validar datos de entrada
        //TODO: Validar que es un user admin
        int boxes = beerCalculator.calculateBoxesOfBeers(meet, beersByBox, attendants);
        Beer beer = getBeer(boxes);
        
        return beer;
    }
    
    public Beer getBeer(int boxes) {
        Beer beer = new Beer();
        beer.setAmountOfBoxes(boxes);
        return beer;
    }
}

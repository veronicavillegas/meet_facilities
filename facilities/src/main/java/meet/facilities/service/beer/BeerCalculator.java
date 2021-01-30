package meet.facilities.service.beer;

import java.io.IOException;

import org.springframework.stereotype.Service;

import meet.facilities.dto.Meet;
import meet.facilities.dto.Weather;
import meet.facilities.exception.InvalidInputDataException;
import meet.facilities.exception.NotFoundWeatherException;
import meet.facilities.service.weather.WeatherService;
import meet.facilities.util.Constant;

@Service
public class BeerCalculator {
    private WeatherService weatherService;

    public BeerCalculator(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    public int calculateBoxesOfBeers(Meet meet, int beersByBox, int attendants)
            throws IOException, NotFoundWeatherException, InvalidInputDataException {
        Weather weather = weatherService.getWeather(meet.getDate(), meet.getLocation());
        
        // Si no me pude traer el pronóstico, calculo cervezas para un día de calor
        if (weather != null) {
            if (isItCold(weather)) {
                return getBoxes(Constant.BEERS_COLD_DAY, attendants, beersByBox);
            } else if (isItWarm(weather)) {
                return getBoxes(Constant.BEERS_WARM_DAY, attendants, beersByBox);
            } else {
                return getBoxes(Constant.BEERS_HOT_DAY, attendants, beersByBox);
            } 
        } else {
            //Acá podría alertar algún monitor para indicar que hay problemas con la api del pronóstico
            return getBoxes(Constant.BEERS_HOT_DAY, attendants, beersByBox);
        }
    }

    private int getBoxes(double beersByPersons, int attendantsAmount, int beersByBox) {
        double boxes = (beersByPersons * attendantsAmount) / beersByBox;
            
        return (int)Math.ceil(boxes);
    }

    private boolean isItWarm(Weather weather) {
        return weather.getMaxTemp() < Constant.MAX_TEMP && 
                weather.getMaxTemp() > Constant.MIN_TEMP;
    }

    private boolean isItCold (Weather weather) {
        return weather.getMaxTemp() < Constant.MIN_TEMP;
    }
}


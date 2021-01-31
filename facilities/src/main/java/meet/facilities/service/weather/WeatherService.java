package meet.facilities.service.weather;

import java.io.IOException;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import meet.facilities.dto.Location;
import meet.facilities.dto.Weather;
import meet.facilities.exception.InvalidInputDataException;
import meet.facilities.exception.NotFoundWeatherException;
import meet.facilities.util.Constant;
import meet.facilities.util.Helper;
import meet.facilities.validator.WeatherDataValidator;

@Service
public class WeatherService {
	WeatherManager weatherManager;
	WeatherDataValidator weatherDataValidator;

	public WeatherService(WeatherManager weatherManager, WeatherDataValidator weatherDataValidator) {
		this.weatherManager = weatherManager;
		this.weatherDataValidator = weatherDataValidator;
	}

	public Weather getWeather(String country, String city, String givenDate)
			throws IOException, NotFoundWeatherException, InvalidInputDataException  {
				Date date = Helper.parseToDate(givenDate);
				Location location = getLocation(city, country);

				weatherDataValidator.validateDate(date);
				weatherDataValidator.validateLocation(location);

				return getWeather(date, location);
	}

	public Weather getWeather(Date date, Location location) throws IOException, NotFoundWeatherException {
		List<Weather> weatherList = weatherManager.getWeather(location);
		Date dateToFind = getDateToFind(date);

		return getWeatherOfDate(weatherList, dateToFind);
	}

	private Date getDateToFind(Date date) {
		Date dateToFind = date;
		dateToFind.setMinutes(0);
		dateToFind.setSeconds(0);

		if (date.getHours() < 13) {
			dateToFind.setHours(12);
		} else {
			dateToFind.setHours(15);
		}
		return dateToFind;
	}

	private Weather getWeatherOfDate(List<Weather> weatherList, Date date) throws NotFoundWeatherException {
		if (weatherList != null && weatherList.size() > 1) {
			for(Weather weather : weatherList) {
				if(weather.getDate().compareTo(date) == 0) {
					return weather;
				}
			}
		} else if(weatherList.size() == 1) {
			//Si estoy en este caso es que la api está caída y me responde por Hystrix
			return weatherList.get(0);
		}
		
		throw new NotFoundWeatherException("Forecast not found for given date");
	}

	private Location getLocation(String city, String country) {
        Location location = new Location();
        location.setCity(city);
        location.setCountry(country);

        return location;
    }
	
}

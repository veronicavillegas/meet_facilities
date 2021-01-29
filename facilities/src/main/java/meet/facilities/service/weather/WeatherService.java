package meet.facilities.service.weather;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import meet.facilities.dto.Location;
import meet.facilities.dto.Weather;
import meet.facilities.exception.InvalidInputDataException;
import meet.facilities.exception.NotFoundWeatherException;
import meet.facilities.validator.WeatherDataValidator;

@Service
public class WeatherService {
	WeatherManager weatherManager;
	WeatherDataValidator weatherDataValidator;

	public WeatherService(WeatherManager weatherManager, WeatherDataValidator weatherDataValidator) {
		this.weatherManager = weatherManager;
		this.weatherDataValidator = weatherDataValidator;
	}

	public Weather getWeather(Date date, Location location)
			throws IOException, NotFoundWeatherException, InvalidInputDataException {
		weatherDataValidator.validateDate(date);
		weatherDataValidator.validateLocation(location);
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
		if (weatherList != null && weatherList.size() > 0) {
			for(Weather weather : weatherList) {
				if(weather.getDate().compareTo(date) == 0) {
					return weather;
				}
			}
		}
		
		throw new NotFoundWeatherException("Forecast not found for given date");
	}

	
}

package meet.facilities.service.weather;

import java.io.IOException;
import java.util.Date;

import org.springframework.stereotype.Service;

import meet.facilities.dto.Location;
import meet.facilities.dto.Weather;

@Service
public class WeatherService {
	WeatherManager weatherManager;

	public WeatherService(WeatherManager weatherManager) {
		this.weatherManager = weatherManager;
	}

	public Weather getWeather(Date date, Location location) throws IOException {
		return (Weather)(weatherManager.getWeather(location).get(0));
	}   
}

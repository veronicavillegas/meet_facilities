package meet.facilities.service.weather;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import meet.facilities.cache.CacheService;
import meet.facilities.client.WeatherClient;
import meet.facilities.client.response.Forecast;
import meet.facilities.dto.Location;
import meet.facilities.dto.Weather;

@Service
public class WeatherManager {
    WeatherClient weatherClient;
    CacheService cacheService;

    public WeatherManager(WeatherClient weatherClient, CacheService cacheService) {
        this.weatherClient = weatherClient;
        this.cacheService = cacheService;
    }

    public List<Weather> getWeather(Location location) throws IOException {
        Forecast forecast = cacheService.getForecast(location); 

        if (forecast == null) {
            forecast = weatherClient.getForecast(location);
        } 

        if (forecast != null) {
            cacheService.saveForecast(location, forecast);
        }
        
        return getWeatherList(forecast);
    }

    private List<Weather> getWeatherList(Forecast forecast) {
        List<Weather> weatherList = new ArrayList<Weather>();
        /*
        forecast.getList().forEach(item -> {
            Weather weather = new Weather();
            weather.setMaxTemp(item.getMain().getTemp_max());
            weather.setDate(item.getMain().get);
            weatherList.add(weather);
        });*/
        return weatherList;
    }
}

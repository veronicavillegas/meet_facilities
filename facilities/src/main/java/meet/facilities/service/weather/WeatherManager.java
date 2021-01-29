package meet.facilities.service.weather;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import meet.facilities.cache.CacheService;
import meet.facilities.client.WeatherClient;
import meet.facilities.client.response.Forecast;
import meet.facilities.dto.Location;
import meet.facilities.dto.Weather;
import meet.facilities.util.Constant;

@Service
public class WeatherManager {
    WeatherClient weatherClient;
    CacheService cacheService;

    public WeatherManager(WeatherClient weatherClient, CacheService cacheService) {
        this.weatherClient = weatherClient;
        this.cacheService = cacheService;
    }

    public List<Weather> getWeather(Location location) throws IOException {
        /*
        Forecast forecast = cacheService.getForecast(location); 

        if (forecast == null) {
            forecast = weatherClient.getForecast(location);
        } 

        if (forecast != null) {
            cacheService.saveForecast(location, forecast);
        }*/
        Forecast forecast = weatherClient.getForecast(location);

        return getWeatherList(forecast);
    }

    private List<Weather> getWeatherList(Forecast forecast) {
        List<Weather> weatherList = new ArrayList<Weather>();
        
        forecast.getList().forEach(item -> {
            Weather weather = new Weather();
            weather.setMaxTemp(item.getMain().getTemp_max());
            Date date;
            try {
                date = new SimpleDateFormat("yyyy-mm-dd").parse(item.getDt_txt());
            } catch (ParseException e) {
                return;
            }
            weather.setDate(date);
            weatherList.add(weather);
        });
        return weatherList;
    }
}

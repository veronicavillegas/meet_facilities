package meet.facilities.service.weather;

import org.springframework.stereotype.Service;

import meet.facilities.cache.CacheService;
import meet.facilities.dto.Weather;

@Service
public class WeatherManager {
    WeatherService weatherService;
    CacheService cacheService;

    public WeatherManager(WeatherService weatherService, CacheService cacheService) {
        this.weatherService = weatherService;
        this.cacheService = cacheService;
    }

    public Weather getWeather() {
        Weather weather = new Weather();
        //TODO: Busco si está en caché
        //Si no está, busco en rest api
        //Si está, lo guardo en caché
        return weather;
    }
}

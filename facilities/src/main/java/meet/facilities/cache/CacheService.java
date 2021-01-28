package meet.facilities.cache;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import meet.facilities.client.MemcachedClient;
import meet.facilities.client.response.Forecast;
import meet.facilities.dto.Location;

@Service
public class CacheService {
    MemcachedClient memcachedClient;

    @Autowired
    public CacheService(MemcachedClient memcachedClient) {
        this.memcachedClient = memcachedClient;
    }

    public Forecast getForecast(Location location) {
        String key = location.getCountry() + "_" + location.getCity();
        return (Forecast)memcachedClient.get(key);
    }

    public void saveForecast(Location location, Forecast forecast) {
        String key = location.getCountry() + "_" + location.getCity();
        memcachedClient.save(key, forecast);
    }
}

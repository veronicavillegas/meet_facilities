package meet.facilities.client;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import meet.facilities.client.response.Forecast;
import meet.facilities.client.response.Weather;
import meet.facilities.dto.Location;

@Service
public class WeatherClient {
    WeatherBitClient weatherBitClient;

    @Autowired
    public WeatherClient(WeatherBitClient weatherBitClient) {
        this.weatherBitClient = weatherBitClient;
    }

    public Forecast getForecast(Location location) throws IOException {
        return weatherBitClient.getForecast(location);
    }
}

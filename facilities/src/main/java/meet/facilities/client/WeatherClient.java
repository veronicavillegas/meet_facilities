package meet.facilities.client;

import java.io.IOException;

import org.springframework.stereotype.Service;

import meet.facilities.client.response.Forecast;
import meet.facilities.client.response.Weather;
import meet.facilities.dto.Location;

@Service
public class WeatherClient {
    WeatherBitClient weatherBitClient;

    public Forecast getForecast(Location location) throws IOException {
        return weatherBitClient.getForecast(location);
    }
}

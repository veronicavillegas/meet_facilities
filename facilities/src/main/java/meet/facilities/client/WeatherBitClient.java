package meet.facilities.client;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Key;

import org.apache.http.HttpStatus;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.stereotype.Component;

import meet.facilities.client.response.Forecast;
import meet.facilities.dto.Location;
import meet.facilities.dto.Weather;

@Component
public class WeatherBitClient {
    private static HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static JsonFactory JSON_FACTORY = new JacksonFactory();

    private final int CONNECTION_TIMEOUT = 8000;
    private final int READ_TIMEOUT = 8000;
    private final int NUMBER_OF_RETRIES = 3;

    // TODO: HISTRIX
    public Forecast getForecast(Location location) throws IOException {
        String url = "https://community-open-weather-map.p.rapidapi.com/forecast?units=metric&";
        String query = "q=" + location.getCity() + "," + location.getCountry();

        HttpRequest request = getRequest(url+query);

        request.setHeaders(getHeaders());

        HttpResponse response = request.execute();
        
        if (response.getStatusCode() != HttpStatus.SC_OK) {
            // TODO: Analizar si es correcto devolver null
            return null;
        } else {
            return (Forecast)response.parseAs(Forecast.class);
        }
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-key", "eac81d8209msh11bcb955ab0b513p196cc0jsn8828566b4459");
        headers.set("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com");
    
        return headers;
    }

    private HttpRequest getRequest(String urlString) throws IOException {
        HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(
                (HttpRequest request) -> {
                request.setParser(new JsonObjectParser(JSON_FACTORY));
                }); 

        GenericUrl url = new GenericUrl(urlString);

        HttpRequest request = requestFactory.buildGetRequest(url);

        request.setNumberOfRetries(NUMBER_OF_RETRIES);
        request.setConnectTimeout(CONNECTION_TIMEOUT);
        request.setReadTimeout(READ_TIMEOUT);

        return request;
    }
}
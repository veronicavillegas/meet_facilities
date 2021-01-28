package meet.facilities.client.response;

import java.util.List;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Forecast extends GenericJson {
    @Key
    private List<Weather> list;
    @Key
    private String dt_txt;
}

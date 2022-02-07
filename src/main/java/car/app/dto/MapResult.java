package car.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MapResult {
    private Map data;
    private int total;

    public static MapResult of(Map data, int total) {
        return new MapResult(data, total);
    }
}

package car.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult {
    private List data;
    private int total;

    public static PageResult of(List data, int total) {
        return new PageResult(data, total);
    }
}

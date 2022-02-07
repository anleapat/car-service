package car.app.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class GroupByDto {
    @JsonFormat(pattern = "M/D/YYYY", timezone = "GMT+8")
    private Date groupBy;
    private List data;
}

package car.app.dto;

import car.app.entity.Car;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class CarDto extends Car {
    @JsonFormat(pattern = "M/D/YYYY", timezone = "GMT+8")
    private Date produceDateFrom;

    @JsonFormat(pattern = "M/D/YYYY", timezone = "GMT+8")
    private Date produceDateTo;

    private boolean groupBy;

    private String orderByValue;

    private String produceYearStr;
}

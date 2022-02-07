package car.app.entity;

import car.app.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Car extends BaseDto {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String origin;

    @JsonFormat(pattern = "M/D/YYYY", timezone = "GMT+8")
    private Date produceYear;

    @JsonSerialize(using = ToStringSerializer.class)
    private Integer horsePower;

    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal milesPerGallon;

    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal acceleration;

    @JsonSerialize(using = ToStringSerializer.class)
    private Integer displacement;

    @JsonSerialize(using = ToStringSerializer.class)
    private Integer weightInLbs;

    private String carName;

    @JsonSerialize(using = ToStringSerializer.class)
    private Integer cylinders;

}
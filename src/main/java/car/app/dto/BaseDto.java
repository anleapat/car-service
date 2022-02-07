package car.app.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseDto implements Serializable {
    private Long createBy;

    private Date createDate;

    private Long lastUpdateBy;

    private Date lastUpdateDate;

    private Integer lastUpdateVersion;
}

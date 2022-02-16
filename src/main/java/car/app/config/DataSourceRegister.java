package car.app.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Data
public class DataSourceRegister {
    private DataSource defaultDataSource;
    private Map<Object, Object> dataSources = new HashMap<>();
}

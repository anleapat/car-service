package car.app.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class DataSourceConfig {
    /**
     * init datasource
     * @param environment evn
     * @return DataSourceRegister
     */
    @Bean
    public DataSourceRegister initDataSource(Environment environment) {
        Map<Object, Object> dataSources = new HashMap<>();
        // init master dataSource
        DataSource masterDataSource = buildDataSource(environment, "");
        dataSources.put("master", masterDataSource);

        // init all slaves dataSource
        String slaves = environment.getProperty("spring.datasource.slaves");
        String[] slaveDs = slaves.split(",");
        for(String ds : slaveDs) {
            DataSource slave = buildDataSource(environment, "." + ds);
            dataSources.put(ds, slave);
            DataSourceContextHolder.addDataSource(ds);
        }
        DataSourceRegister dataSourceRegister = new DataSourceRegister();
        dataSourceRegister.setDefaultDataSource(masterDataSource);
        dataSourceRegister.setDataSources(dataSources);
        return dataSourceRegister;
    }

    @Bean
    public AbstractRoutingDataSource abstractRoutingDataSource(DataSourceRegister dataSourceRegister) {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setDefaultTargetDataSource(dataSourceRegister.getDefaultDataSource());
        dynamicDataSource.setTargetDataSources(dataSourceRegister.getDataSources());
        return dynamicDataSource;
    }

    private static final String DATASOURCE_POOL_TYPE = "org.apache.tomcat.jdbc.pool.DataSource";

    private DataSource buildDataSource(Environment env, String prefix) {
        try {
            Class<? extends DataSource> dataSourceType = (Class<? extends DataSource>) Class.forName(DATASOURCE_POOL_TYPE);
            String driverClassName = env.getProperty("spring.datasource" + prefix + ".driver-class-name");
            String url = env.getProperty("spring.datasource" + prefix + ".url");
            String username = env.getProperty("spring.datasource" + prefix + ".username");
            String password = env.getProperty("spring.datasource" + prefix + ".password");
            DataSourceBuilder factory = DataSourceBuilder.create().driverClassName(driverClassName).url(url)
                    .username(username).password(password).type(dataSourceType);
            DataSource ds = (DataSource) factory.build();
            ds.setInitialSize(7);
            ds.setMaxActive(77);
            ds.setMaxIdle(17);
            ds.setMinIdle(7);
            ds.setRemoveAbandoned(true);
            ds.setRemoveAbandonedTimeout(37);
            ds.setMaxWait(7000);
            ds.setValidationQuery("select 1");
            ds.setValidationQueryTimeout(3);
            ds.setValidationInterval(70000);
            ds.setTestOnBorrow(true);
            ds.setTestOnReturn(false);
            ds.setTestWhileIdle(false);
            ds.setTimeBetweenEvictionRunsMillis(7000);
            ds.setMinEvictableIdleTimeMillis(70000);
            return ds;
        } catch (ClassNotFoundException e) {
            log.error("init datasource error", e);
        }
        return null;
    }

}

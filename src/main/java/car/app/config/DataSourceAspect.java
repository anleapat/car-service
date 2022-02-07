package car.app.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Order(-1) // make sure its priority
@Component
public class DataSourceAspect {
    @Before("@annotation(tds)")
    public void changeDataSource(JoinPoint point, TargetDataSource tds) throws Throwable {
        String ds = tds.name();
        if (DataSourceContextHolder.containDataSource(ds)) {
            log.info("Use DataSource: {} > {}", tds.name(), point.getSignature());
            DataSourceContextHolder.switchDataSource(tds.name());
        } else {
            if (DataSourceContextHolder.switchSlaveDataSourceRandomly(ds)) {
                return;
            }
            log.error("datasource[{}] not exists, use default > {}", tds.name(), point.getSignature());
        }
    }

    @After("@annotation(tds)")
    public void restoreDataSource(JoinPoint point, TargetDataSource tds) {
        log.info("Revert DataSource : {} > {}", tds.name(), point.getSignature());
        DataSourceContextHolder.clearDataSource();
    }
}
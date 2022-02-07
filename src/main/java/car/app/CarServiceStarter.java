package car.app;

import car.app.config.DataSourceRegister;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = {"car.app"})
@Import({DataSourceRegister.class})
public class CarServiceStarter {
    public static void main(String[] args) {
        SpringApplication.run(CarServiceStarter.class, args);
    }
}

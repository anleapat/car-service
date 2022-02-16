package car.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"car.app"})
public class CarServiceStarter {
    public static void main(String[] args) {
        SpringApplication.run(CarServiceStarter.class, args);
    }
}

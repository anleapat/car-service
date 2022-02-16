package car.app;

import car.app.entity.Car;
import car.app.dto.CarDto;
import car.app.dto.PageResult;
import car.app.service.CarService;
import com.google.gson.Gson;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
public class CarServiceTest {
    @Autowired
    CarService carService;

    private static final Gson gs = new Gson();

//    @Test
    public void initCars() {
        List<Map<String, Object>> list = (List<Map<String, Object>>) gs.fromJson(readCarData(), List.class);
        List<Car> carList = new ArrayList<>();
        list.forEach(item -> {
            Car car = new Car();
            car.setCarName(MapUtils.getString(item, "Name"));
            car.setAcceleration(new BigDecimal(MapUtils.getString(item, "Acceleration")));
            car.setCylinders(MapUtils.getInteger(item, "Cylinders"));
            car.setDisplacement(MapUtils.getInteger(item, "Cylinders"));
            car.setHorsePower(MapUtils.getInteger(item, "Horsepower"));
            String mpg = MapUtils.getString(item, "Miles_per_Gallon");
            if (StringUtils.isNoneBlank(mpg)) {
                car.setMilesPerGallon(new BigDecimal(mpg));
            }
            car.setOrigin(MapUtils.getString(item, "Origin"));
            try {
                car.setProduceYear(DateUtils.parseDate(MapUtils.getString(item, "Year"), "YYYY-MM-DD"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            car.setWeightInLbs(MapUtils.getInteger(item, "Weight_in_lbs"));
            carList.add(car);
        });
        List<Car> carList1 = carList.stream().sorted((c2, c1) -> c2.getProduceYear().compareTo(c1.getProduceYear())).collect(Collectors.toList());
        carList1.forEach(car -> carService.saveCar(car));
    }

    @Test
    public void pageQueryTest() {
        for (int i = 0; i < 37; i++) {
            CarDto car = new CarDto();
            car.setCarName("dodge");
            PageResult pageResult = carService.pageQuery(car, 1, 7);
            System.out.println(gs.toJson(pageResult));
            try {
                Thread.sleep(1000L);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Test
    public void findTest() {
        for (int i = 0; i < 37; i++) {
            Car car = carService.find(1L);
            System.out.println(gs.toJson(car));
            try {
                Thread.sleep(1000L);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    private String readCarData() {
        try {
            return FileUtils.readFileToString(new File("/home/henry/data/rn/car-service/src/main/resources/data/carJson.json"), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

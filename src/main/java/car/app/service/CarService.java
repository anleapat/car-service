package car.app.service;

import car.app.config.TargetDataSource;
import car.app.entity.Car;
import car.app.dto.CarDto;
import car.app.dto.PageResult;
import car.app.mapper.CarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarService {
    @Autowired
    CarMapper carMapper;

    public int saveCar(Car car) {
        return carMapper.insert(car);
    }

    public int updateCar(Car car) {
        return carMapper.update(car);
    }

    @TargetDataSource(name = "slave3")
    public Car find(Long id) {
        return carMapper.select(id);
    }

    @TargetDataSource(name = "slave")
    public PageResult pageQuery(CarDto car, int curPage, int pageSize) {
        int total = carMapper.count(car);
        if (total > 0) {
            int offset = (curPage - 1) * pageSize;
            List<Car> cars = carMapper.searchCars(car, offset, pageSize);
            return PageResult.of(cars, total);
        }
        return PageResult.of(new ArrayList(), 0);
    }
}

package car.app.mapper;

import car.app.entity.Car;
import car.app.dto.CarDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CarMapper {
    int insert(Car record);

    Car select(Long id);

    int update(Car record);

    int count(@Param("car") Car car);

    List<Car> searchCars(@Param("car") CarDto car, @Param("offset") Integer offset, @Param("limit") Integer limit);
}
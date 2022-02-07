package car.app.controller;

import car.app.dto.BaseResult;
import car.app.dto.CarDto;
import car.app.dto.GroupByDto;
import car.app.dto.PageResult;
import car.app.entity.*;
import car.app.service.CarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Api(value = "car service")
@RestController
public class CarController {
    @Autowired
    CarService carService;

    @ApiOperation(value = "searchCars", httpMethod = "POST")
    @RequestMapping(value = "/searchCars/{curPage}/{pageSize}", method = RequestMethod.POST)
    public BaseResult searchCars(@RequestBody CarDto car, @PathVariable("curPage") int curPage, @PathVariable("pageSize") int pageSize) {
        try {
            car.setOrderByValue(" id desc ");
            PageResult pageResult = carService.pageQuery(car, curPage, pageSize);
            if (car.isGroupBy() && pageResult.getTotal() > 0) {
                Map<Date, List<Car>> map = (Map<Date, List<Car>>) pageResult.getData().stream().collect(Collectors.groupingBy(Car::getProduceYear));
                List<GroupByDto> groupByDtos = new ArrayList<>();
                map.keySet().forEach(key -> {
                    GroupByDto groupByDto = new GroupByDto();
                    groupByDto.setData(map.get(key));
                    groupByDto.setGroupBy(key);
                    groupByDtos.add(groupByDto);
                });
                List<GroupByDto> groupByDtos1 = null;
                if (groupByDtos.size() > 1) {
                    groupByDtos1 = groupByDtos.stream().sorted((d1, d2) -> (d2.getGroupBy()).compareTo((d1.getGroupBy()))).collect(Collectors.toList());
                } else {
                    groupByDtos1 = groupByDtos;
                }
                PageResult gbPageResult = PageResult.of(groupByDtos1, pageResult.getTotal());
                return BaseResult.ok(gbPageResult);
            }
            return BaseResult.ok(pageResult);
        } catch (Exception ex) {
            log.error("findCarList failed", ex);
            return BaseResult.error("failed");
        }
    }

    @ApiOperation(value = "getCarInfo", httpMethod = "GET")
    @RequestMapping(value = "/getCarInfo/{id}", method = RequestMethod.GET)
    public BaseResult getCarInfo(@PathVariable("id") long id) {
        try {
            Car car = carService.find(id);
            return BaseResult.ok(car);
        } catch (Exception ex) {
            log.error("getCarInfo failed", ex);
            return BaseResult.error("failed");
        }
    }
}

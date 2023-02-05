package com.codesquad.autobid.car.repository;

import com.codesquad.autobid.car.domain.Car;
import com.codesquad.autobid.car.repository.rowMapper.CarRowMapper;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarRepository extends CrudRepository<Car, Long> {
    @Query(value = "SELECT * FROM car WHERE user_id = :id", rowMapperClass = CarRowMapper.class)
    List<Car> findCarsByUserId(@Param("id") Long id);
}

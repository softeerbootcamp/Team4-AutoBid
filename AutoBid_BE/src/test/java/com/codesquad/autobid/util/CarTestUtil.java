package com.codesquad.autobid.util;

import com.codesquad.autobid.car.domain.Car;
import com.codesquad.autobid.car.domain.State;
import com.codesquad.autobid.car.domain.Type;
import com.codesquad.autobid.user.domain.User;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CarTestUtil {
    public static List<Car> getNewCars(Long userId, int count) {
        List<Car> cars = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            cars.add(new Car(
                    null,
                    AggregateReference.to(userId),
                    State.NOT_FOR_SALE,
                    Type.ETC,
                    100f,
                    "id#" + count,
                    "name#" + count,
                    "sellname#" + count,
                    LocalDateTime.now(),
                    LocalDateTime.now()));
        }
        return cars;
    }

    public static Car saveCar(User user) {
        try {
            Car car = CarTestUtil.getNewCars(user.getId(), 1).get(0);
            Field id = car.getClass().getDeclaredField("id");
            id.setAccessible(true);
            id.set(car, 1l);
            return car;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null;
        }
    }
}

package com.codesquad.autobid.car.util;

import com.codesquad.autobid.car.domain.Car;
import com.codesquad.autobid.car.domain.Distance;
import com.codesquad.autobid.car.domain.State;
import com.codesquad.autobid.car.domain.Type;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CarTestUtil {
    public static List<Car> getNewCars(Long userId, int count) {
        List<Car> cars = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            cars.add(new Car(
                    null,
                    userId,
                    State.NOT_FOR_SALE,
                    Type.ETC,
                    Distance.from(count + " KM"),
                    "id#" + count,
                    "name#" + count,
                    "sellname#" + count,
                    LocalDateTime.now(),
                    LocalDateTime.now()));
        }
        return cars;
    }
}

package com.codesquad.autobid.car.domain;

import com.codesquad.autobid.handler.car.vo.CarVO;
import com.codesquad.autobid.user.domain.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("car")
public class Car {

    @Id
    @Column("car_id")
    private Long id;
    @Column("user_id")
    private AggregateReference<User, Long> userId;
    @Column("car_state")
    private State state;
    @Column("car_type")
    private Type type;
    @Column("car_distance")
    @Embedded(onEmpty = Embedded.OnEmpty.USE_EMPTY)
    private Distance distance;
    @Column("car_carid")
    private String carId;
    @Column("car_name")
    private String name;
    @Column("car_sellname")
    private String sellname;
    @CreatedDate
    private LocalDateTime createdAt;
    @Column("updated_at")
    private LocalDateTime updatedAt;

    public Car(Long id, Long userId, State state, Type type, Distance distance, String carId, String name, String sellname, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = AggregateReference.to(userId);
        this.state = state;
        this.type = type;
        this.distance = distance;
        this.carId = carId;
        this.name = name;
        this.sellname = sellname;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Car from(CarVO carVO, Long userId) {
        return new Car(null,
                userId,
                State.NOT_FOR_SALE,
                carVO.getType(),
                Distance.from(carVO.getAvailableDistanceVO()),
                carVO.getCarId(),
                carVO.getName(),
                carVO.getSellname(),
                LocalDateTime.now(),
                LocalDateTime.now());
    }

    public void setUser(AggregateReference<User, Long> userId) {
        this.userId = userId;
    }

    public AggregateReference<User, Long> getUser() {
        return userId;
    }

    public Long getId() {
        return id;
    }

    public String getCarId() {
        return carId;
    }

    public void update(CarVO carVO) {
        if (carVO == null) {
            return;
        }
        this.carId = carVO.getCarId();
        this.name = carVO.getName();
        this.sellname = carVO.getSellname();
        this.type = carVO.getType();
    }
}

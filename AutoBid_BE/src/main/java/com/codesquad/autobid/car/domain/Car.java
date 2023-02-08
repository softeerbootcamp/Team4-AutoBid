package com.codesquad.autobid.car.domain;

import com.codesquad.autobid.handler.car.vo.CarVO;
import com.codesquad.autobid.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("car")
@Getter
@AllArgsConstructor
@ToString
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
    @Column("car_sellName")
    private String sellName;
    @CreatedDate
    @Column("created_at")
    private LocalDateTime createdAt;
    @Column("updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static Car from(CarVO carVO, Long userId) {
        return new Car(null,
                AggregateReference.to(userId),
                State.NOT_FOR_SALE,
                carVO.getType(),
                Distance.from(carVO.getAvailableDistanceVO()),
                carVO.getCarId(),
                carVO.getName(),
                carVO.getSellName(),
                LocalDateTime.now(),
                LocalDateTime.now());
    }

    public void update(CarVO carVO) {
        if (carVO == null) {
            return;
        }
        this.carId = carVO.getCarId();
        this.name = carVO.getName();
        this.sellName = carVO.getSellName();
        this.type = carVO.getType();
    }
}

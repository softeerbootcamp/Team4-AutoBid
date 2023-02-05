package com.codesquad.autobid.car.domain;

import com.codesquad.autobid.handler.car.vo.CarVO;
import com.codesquad.autobid.user.domain.Users;
import org.springframework.data.annotation.AccessType;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("car")
public class Car {

    @Id
    @Column("car_id")
    private Long id;
    @Column("user_id")
    @AccessType(AccessType.Type.PROPERTY)
    private Users user;

    // @Column("car_state")
    // @AccessType(AccessType.Type.PROPERTY)
    // private State state;

    // public void setState(int code) {
    //     System.out.println(code);
    //     this.state = State.findByCode(code);
    // }

    // @Column("car_category" )
    // private Category category;
    private Type type;
    @Column("car_distance")
    private Long distance;
    // 현대차 API에서 사용하는 차량 식별용 아이디
    @Column("car_carid")
    private String carId;
    @Column("car_nickname")
    private String nickname;
    @Column("car_name")
    private String name;
    @Column("car_sellname")
    private String sellname;
    @Column("created_at")
    private LocalDateTime createdAt;
    @Column("updated_at")
    private LocalDateTime updatedAt;

    private Car() {
    }

    public static Car from(CarVO carVO) {
        Car car = new Car();
        car.carId = carVO.getCarId();
        car.nickname = carVO.getCarNickname();
        car.type = Type.findByDescription(carVO.getCarType());
        car.name = carVO.getCarName();
        car.sellname = carVO.getCarSellname();
        return car;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", user=" + user +
                //", category=" + category +
                ", type=" + type +
                ", distance=" + distance +
                ", carId='" + carId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", name='" + name + '\'' +
                ", sellname='" + sellname + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}

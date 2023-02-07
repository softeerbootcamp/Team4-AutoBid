package com.codesquad.autobid.car.repository.rowMapper;

import com.codesquad.autobid.car.domain.Car;
import com.codesquad.autobid.car.domain.Distance;
import com.codesquad.autobid.car.domain.State;
import com.codesquad.autobid.car.domain.Type;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class CarRowMapper implements RowMapper {

    @Override
    public Car mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long id = rs.getLong("car_id");
        Long userId = rs.getLong("user_id");
        State state = State.valueOf(rs.getString("car_state"));
        Type type = Type.valueOf(rs.getString("car_type"));
        Distance distance = Distance.from(String.join(" ", List.of(rs.getString("car_distance"), rs.getString("car_distance_unit"))));
        String carId = rs.getString("car_carid");
        String name = rs.getString("car_name");
        String sellName = rs.getString("car_sellname");
        LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
        LocalDateTime updatedAt = rs.getTimestamp("updated_at").toLocalDateTime();
        return new Car(id, userId, state, type, distance, carId, name, sellName, createdAt, updatedAt);
    }
}

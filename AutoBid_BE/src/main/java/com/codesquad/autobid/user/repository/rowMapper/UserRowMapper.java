package com.codesquad.autobid.user.repository.rowMapper;

import com.codesquad.autobid.user.domain.UserVO;
import com.codesquad.autobid.user.domain.Users;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class UserRowMapper implements RowMapper {


    @Override
    public Users mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long id = rs.getLong("user_id");
        String userUid = rs.getString("user_uid");
        String userEmail = rs.getString("user_email");
        String userBirthdate = rs.getString("user_birthdate");
        String userMobilenum = rs.getString("user_mobilenum");
        LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
        LocalDateTime updatedAt = rs.getTimestamp("updated_at").toLocalDateTime();
//        return new Users(id, userUid, userEmail, userBirthdate, userMobilenum, createdAt, updatedAt);
        return new Users();
    }
}

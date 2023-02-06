package com.codesquad.autobid.user.repository;

import com.codesquad.autobid.user.domain.Users;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;

@Repository
public class UserRepository {

    private final JdbcTemplate template;

    public UserRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    public Users save(Users user) {

        String sql = "INSERT INTO users(" +
                "user_uid, user_email, user_name, user_mobilenum,user_birthdate, created_at, updated_at, refresh_token)" +
                " values (?,?,?,?,?,?,?,?)";

        template.update(sql,
                "12341234",
                user.getEmail(),
                user.getName(),
                user.getMobilenum(),
                user.getBirthdate(),
                user.getCreateAt(),
                user.getUpdateAt(),
                user.getRefreshToken()
        );

        return user;
    }

//    public Users findById(String userId) {
//        String sql = "SELECT * FROM where user_id = ?";
//        return template.queryForObject(sql, userRowMapper(), userId);
//    }

//    private RowMapper<Users> userRowMapper() {
//        return (rs, rowNum) -> {
//            Users user = new Users();
//            user.setUid(rs.getString("user_uid"));
//            user.setEmail(rs.getString("user_email"));
//            user.setName(rs.getString("user_name"));
//            user.setMobilenum(rs.getString("user_mobilenum"));
//            user.setBirthdate(rs.getString("user_birthdate"));
//            user.setCreateAt(rs.getObject("create_at", LocalDateTime.class));
//            user.setCreateAt(rs.getObject("update_at", LocalDateTime.class));
//            user.setRefreshToken(rs.getString("refresh_token"));
//            return user;
//        };
//    }

    public void update(String userId, String userMobilenum) {
        String sql = "UPDATE users SET user_mobilenum = ? WHERE user_id= ?";
        template.update(sql, userMobilenum, userId);
    }

    public void delete(String userId, String userMobilenum) {
        String sql = "DELETE from users WHERE user_id = ?";
        template.update(sql, userId);
    }
}

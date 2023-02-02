package com.codesquad.autobid.user.repository;

import com.codesquad.autobid.user.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class UserRepository {

    private final JdbcTemplate template;

    public UserRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    public User save(User user) {
        String sql = "INSERT INTO user(userid, name, phonenumber) values (?,?,?)";
        template.update(sql, user.getUserid(), user.getUsername(), user.getPhonenumber());
        return user;
    }

    public User findById(String user_id) {
        String sql = "SELECT * FROM where userid = ?";
        return template.queryForObject(sql, userRowMapper(), user_id);
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            User user = new User();
            user.setUserid(rs.getString("userid"));
            user.setUsername(rs.getString("username"));
            user.setPhonenumber(rs.getString("phonenumber"));
            return user;
        };
    }

    public void update(String userid, String phonenumber) {
        String sql = "UPDATE user SET phonenumber = ? WHERE userid=?";
        template.update(sql, phonenumber, userid);
    }

    public void delete(String userid, String phonenumber) {
        String sql = "DELETE from user WHERE userid = ?";
        template.update(sql, userid);
    }
}

package com.gxl.kratos.jdbc.test;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

/**
 * User实体映射类
 * 
 * @author gaoxianglong
 */
@Component
public class UserMapper implements RowMapper<User> {
	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setUserinfo_Id(rs.getLong("userinfo_id"));
		return user;
	}
}
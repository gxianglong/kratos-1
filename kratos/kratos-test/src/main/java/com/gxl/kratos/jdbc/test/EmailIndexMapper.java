package com.gxl.kratos.jdbc.test;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

/**
 * EmailIndex实体类映射
 * 
 * @author gaoxianglong
 */
@Component
public class EmailIndexMapper implements RowMapper<EmailIndex> {
	@Override
	public EmailIndex mapRow(ResultSet rs, int rowNum) throws SQLException {
		EmailIndex email = new EmailIndex();
		email.setEmail(rs.getString("email"));
		email.setEmail_hash(rs.getLong("email_hash"));
		email.setUserinfo_id(rs.getLong("userinfo_id"));
		return email;
	}
}
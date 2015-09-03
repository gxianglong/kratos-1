package com.gxl.kratos.jdbc.test;

import org.springframework.stereotype.Component;

/**
 * 对应数据库表[userinfo]
 *
 * @author JohnGao
 */
@Component
public class User {
	private long userinfo_Id;

	public long getUserinfo_Id() {
		return userinfo_Id;
	}

	public void setUserinfo_Id(long userinfo_Id) {
		this.userinfo_Id = userinfo_Id;
	}
}
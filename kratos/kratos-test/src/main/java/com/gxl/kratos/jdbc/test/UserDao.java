package com.gxl.kratos.jdbc.test;

/**
 * 用户信息Dao接口
 * 
 * @author gaoxianglong
 */
public interface UserDao {
	public void addUser(long uid);

	public User getUserbyId(long uid);

	public void addEmailIndex(EmailIndex emailIndex);
	
	public EmailIndex getEmailIndex(EmailIndex emailIndex);
}
package com.gxl.kratos.utils.sequence;

import java.sql.Connection;
import java.sql.DriverManager;

import com.gxl.kratos.utils.exception.DbConnectionException;

/**
 * 数据源链接管理
 * 
 * @author gaoxianglong
 */
public class DbConnectionManager {
	private static String name;
	private static String password;
	private static String jdbcUrl;
	private static String driverClass;

	/**
	 * 初始化数据源信息
	 * 
	 * @author gaoxianglong
	 * 
	 * @param name
	 *            数据库用户帐号
	 * 
	 * @param password
	 *            数据库用户密码
	 * 
	 * @param jdbcUrl
	 *            数据源地址
	 * 
	 * @param driverClass
	 *            数据库驱动
	 */
	public static void init(String name, String password, String jdbcUrl, String driverClass) {
		setName(name);
		setPassword(password);
		setJdbcUrl(jdbcUrl);
		setDriverClass(driverClass);
	}

	/**
	 * 获取数据库链接
	 * 
	 * @author gaoxianglong
	 * 
	 * @exception ClassNotFoundException
	 * 
	 * @return Connection 数据库会话链接
	 */
	public static Connection getConn() {
		Connection conn = null;
		try {
			Class.forName(getDriverClass());
			conn = DriverManager.getConnection(getJdbcUrl(), getName(), getPassword());
		} catch (Exception e) {
			throw new DbConnectionException("获取数据库链接异常...");
		}
		return conn;
	}

	public static String getName() {
		return name;
	}

	public static void setName(String name) {
		DbConnectionManager.name = name;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		DbConnectionManager.password = password;
	}

	public static String getJdbcUrl() {
		return jdbcUrl;
	}

	public static void setJdbcUrl(String jdbcUrl) {
		DbConnectionManager.jdbcUrl = jdbcUrl;
	}

	public static String getDriverClass() {
		return driverClass;
	}

	public static void setDriverClass(String driverClass) {
		DbConnectionManager.driverClass = driverClass;
	}
}
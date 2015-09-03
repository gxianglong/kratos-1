package com.gxl.kratos.jdbc.mysql.sqlparser;

import java.util.List;

/**
 * Sql解析器接口,主要负责解析数据库表名和分库分表条件
 * 
 * @author gaoxianglong
 */
public interface SqlParser {
	/**
	 * 解析Sql(SELECT、INSERT、UPDATE、DELETE)语句中的数据库表名
	 * 
	 * @author gaoxianglong
	 * 
	 * @param sql
	 *            数据库sql语句
	 * 
	 * @return String 解析后的数据库表名
	 */
	public String getTbName(String sql);

	/**
	 * 解析出Sql(SELECT、INSERT、UPDATE、DELETE)语句中的分库分表条件
	 *
	 * @author gaoxianglong
	 *
	 * @param sql
	 *            数据库sql语句
	 * 
	 * @param rules
	 *            分库分表关键字
	 *
	 * @return String 解析后的分库分表条件
	 */
	public long getKey(String sql, List<String> rules);
}
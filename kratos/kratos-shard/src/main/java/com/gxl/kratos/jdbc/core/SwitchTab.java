package com.gxl.kratos.jdbc.core;

import org.springframework.stereotype.Component;

/**
 * 动态对数据库表进行切换,比如通用的数据库表名为tab,那么生成真实的数据库表名可以为tab_0000
 * 
 * @author gaoxianglong
 */
@Component
public class SwitchTab {
	/**
	 * 库内分片模式下设定真正的数据库表名
	 * 
	 * @author gaoxianglong
	 * 
	 * @param dbIndex
	 *            数据库索引
	 * 
	 * @param tbIndex
	 *            数据库表索引
	 * 
	 * @param dbSize
	 *            配置文件中数据库的数量
	 * 
	 * @param dbSize
	 *            配置文件中数据库表的数量
	 * 
	 * @param consistent
	 *            片名是否连续,true为片名连续,false为非片名连续
	 * 
	 * @param tbName
	 *            数据库通用表名
	 * 
	 * @param sql
	 *            需要执行的sql信息
	 * 
	 * @return String 持有真正的数据库表名的sql
	 */
	public String setTabName(int dbIndex, int tbIndex, int dbSize, int tbSize, boolean consistent, String tbName,
			String sql) {
		String tbName_ = null;
		int tbIndexInDb = -1;
		if (consistent) {
			/* 计算平均每个数据库的表的数量 */
			int tbSizeInDb = tbSize / dbSize;
			/* 计算数据库表在指定数据库的索引,其算法为(库索引 * 平均每个数据库的表的数量 + 表索引) */
			tbIndexInDb = dbIndex * tbSizeInDb + tbIndex;
		} else {
			tbIndexInDb = tbIndex;
		}
		if (tbIndexInDb < 10) {
			tbName_ = tbName + "_000" + tbIndexInDb;
		} else if (tbIndexInDb < 100) {
			tbName_ = tbName + "_00" + tbIndexInDb;
		} else if (tbIndexInDb < 1000) {
			tbName_ = tbName + "_0" + tbIndexInDb;
		} else {
			tbName_ = tbName + "_" + tbIndexInDb;
		}
		return sql.replaceFirst(tbName, tbName_);
	}

	/**
	 * 一库一表模式下设定真正的数据库表名
	 * 
	 * @author gaoxianglong
	 * 
	 * @param dbIndex
	 *            数据库索引
	 * 
	 * @param tbName
	 *            数据库通用表名
	 * 
	 * @param sql
	 *            需要执行的sql信息
	 * 
	 * @return String 持有真正的数据库表名的sql
	 */
	public String setTabName(int dbIndex, String tbName, String sql) {
		String tbName_ = null;
		if (dbIndex < 10) {
			tbName_ = tbName + "_000" + dbIndex;
		} else if (dbIndex < 100) {
			tbName_ = tbName + "_00" + dbIndex;
		} else if (dbIndex < 1000) {
			tbName_ = tbName + "_0" + dbIndex;
		} else {
			tbName_ = tbName + "_" + dbIndex;
		}
		return sql.replaceFirst(tbName, tbName_);
	}
}
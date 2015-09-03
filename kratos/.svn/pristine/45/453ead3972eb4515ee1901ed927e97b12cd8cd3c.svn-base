package com.gxl.kratos.utils.sequence;

import java.sql.SQLException;

/**
 * 生成唯一用户生成SequenceId的Dao接口
 * 
 * @author gaoxianglong
 */
public interface CreateSequenceIdDao {
	/**
	 * 根据指定的用户类别获取已申请的占位数量
	 * 
	 * @author gaoxianglong
	 * 
	 * @param type
	 *            类别
	 * 
	 * @throws SQLException
	 * 
	 * @return Long 返回当前占位数量
	 */
	public Long queryUseDatabyType(int type) throws SQLException;

	/**
	 * 根据指定的用户类别创建占位数量
	 * 
	 * @author gaoxianglong
	 * 
	 * @param type
	 *            类别
	 * 
	 * @param useData
	 *            申请占位数量
	 * 
	 * @throws SQLException
	 * 
	 * @return void
	 */
	public void insertSequenceId(int type, Long useData) throws SQLException;

	/**
	 * 获取当前最大的占位数量
	 * 
	 * @author gaoxianglong
	 * 
	 * @throws SQLException
	 * 
	 * @return Long 返回最大的占位边界
	 */
	public Long queryMaxUseData() throws SQLException;

	/**
	 * 根据指定的用户类别更新剩余占位数量和当前占位数量
	 * 
	 * @author gaoxianglong
	 * 
	 * @param type
	 *            类别
	 * 
	 * @param useData
	 *            申请占位数量
	 * 
	 * @throws SQLException
	 * 
	 * @return void
	 */
	public void changeUseData(int type, Long useData) throws SQLException;
}
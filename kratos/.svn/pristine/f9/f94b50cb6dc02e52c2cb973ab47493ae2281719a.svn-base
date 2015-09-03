package com.gxl.kratos.utils.sequence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 生成唯一用户生成SequenceId的Dao接口实现
 * 
 * @author JohnGao
 */
public final class CreateSequenceIdDaoImpl implements CreateSequenceIdDao {
	public static Connection conn;
	private PreparedStatement pre;

	@Override
	public void insertSequenceId(int type, Long useData) throws SQLException {
		if (null == conn || conn.isClosed()) {
			conn = DbConnectionManager.getConn();
			conn.setAutoCommit(false);
		}
		final String SQL = "INSERT INTO kratos_sequenceid(k_type, k_useData) VALUES(?, ?)";
		pre = conn.prepareStatement(SQL);
		pre.setInt(1, type);
		pre.setLong(2, useData);
		pre.execute();
		if (null != pre)
			pre.close();
	}

	@Override
	public Long queryMaxUseData() throws SQLException {
		if (null == conn || conn.isClosed()) {
			conn = DbConnectionManager.getConn();
			conn.setAutoCommit(false);
		}
		Long useData = null;
		final String SQL = "SELECT MAX(ks.k_useData) FROM kratos_sequenceid ks FOR UPDATE";
		pre = conn.prepareStatement(SQL);
		ResultSet resultSet = pre.executeQuery();
		while (resultSet.next())
			useData = resultSet.getLong(1);
		if (null != pre)
			pre.close();
		return useData;
	}

	@Override
	public Long queryUseDatabyType(int type) throws SQLException {
		if (null == conn || conn.isClosed()) {
			conn = DbConnectionManager.getConn();
			conn.setAutoCommit(false);
		}
		Long useData = null;
		final String SQL = "SELECT ks.k_useData FROM kratos_sequenceid ks WHERE " + "ks.k_type = ? FOR UPDATE";
		pre = conn.prepareStatement(SQL);
		pre.setInt(1, type);
		ResultSet resultSet = pre.executeQuery();
		while (resultSet.next())
			useData = resultSet.getLong(1);
		if (null != pre)
			pre.close();
		return useData;
	}

	@Override
	public void changeUseData(int type, Long useData) throws SQLException {
		if (null == conn || conn.isClosed()) {
			conn = DbConnectionManager.getConn();
			conn.setAutoCommit(false);
		}
		final String SQL = "UPDATE kratos_sequenceid ks SET ks.k_useData = ? WHERE ks.k_type = ?";
		pre = conn.prepareStatement(SQL);
		pre.setLong(1, useData);
		pre.setInt(2, type);
		pre.execute();
		if (null != pre)
			pre.close();
	}
}
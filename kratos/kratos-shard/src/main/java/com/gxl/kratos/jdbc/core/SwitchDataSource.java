package com.gxl.kratos.jdbc.core;

import java.util.Arrays;
import java.util.List;
import javax.annotation.Resource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.gxl.kratos.jdbc.datasource.config.DataSourceHolder;
import com.gxl.kratos.jdbc.exception.ShardException;
import com.gxl.kratos.jdbc.mysql.sqlparser.SqlParser;
import com.gxl.kratos.jdbc.shard.DbRule;
import com.gxl.kratos.jdbc.shard.GetKeyName;
import com.gxl.kratos.jdbc.shard.TbRule;

/**
 * 基于SpringAop的方式动态对数据源进行切换
 * 
 * @author gaoxianglong
 */
@Aspect
public class SwitchDataSource {
	private static Logger logger = LoggerFactory.getLogger(SwitchDataSource.class);
	@Resource
	private KratosJdbcTemplate kJdbcTemplate;
	@Resource
	private DataSourceHolder dataSourceHolder;
	@Resource
	private DbRule dbRule;
	@Resource
	private TbRule tbRule;
	@Resource
	private SqlParser sqlParser;
	@Resource
	private SwitchTab switchTab;

	/**
	 * 基于SpringAop的方式对JdbcTemplate下所有的update方法进行拦截
	 * 
	 * @author gaoxianglong
	 * 
	 * @param proceedingJoinPoint
	 *            委托对象的上下文信息
	 * 
	 * @exception Throwable
	 * 
	 * @return Object
	 */
	@Around("execution(* com.gxl.kratos.jdbc.core.KratosJdbcTemplate.update*(..))")
	public Object interceptUpdate(ProceedingJoinPoint proceedingJoinPoint) {
		return execute(proceedingJoinPoint, true);
	}

	/**
	 * 基于SpringAop的方式对JdbcTemplate下所有的query方法进行拦截
	 * 
	 * @author gaoxianglong
	 * 
	 * @param proceedingJoinPoint
	 *            委托对象的上下文信息
	 * 
	 * @exception Throwable
	 * 
	 * @return Object
	 */
	@Around("execution(* com.gxl.kratos.jdbc.core.KratosJdbcTemplate.query*(..))")
	public Object interceptQuery(ProceedingJoinPoint proceedingJoinPoint) {
		return execute(proceedingJoinPoint, false);
	}

	/**
	 * 拦截后需要执行的操作
	 * 
	 * @author gaoxianglong
	 * 
	 * @param proceedingJoinPoint
	 *            委托对象的上下文信息
	 * 
	 * @param operation
	 *            true为master启始索引，false为slave启始索引
	 * 
	 * @exception Throwable
	 * 
	 * @return Object
	 */
	public Object execute(ProceedingJoinPoint proceedingJoinPoint, boolean operation) {
		long befor = System.currentTimeMillis();
		Object result = null;
		/* 获取委托对象指定方法参数 */
		Object[] params = proceedingJoinPoint.getArgs();
		Object param = params[0];
		if (param.getClass() == String.class) {
			final String SQL = (String) param;
			logger.info("之前的数据库sql-->" + SQL);
			/* 检测是否需要执行分库分表操作 */
			if (kJdbcTemplate.getIsShard()) {
				/* 检测分表模式 */
				if (kJdbcTemplate.getShardMode()) {
					/* 一库一片模式 */
					logger.debug("当前采用的分片模式为一库一片");
					params = oneTbshard(SQL, params, operation).toArray();
				} else {
					/* 库内分片模式 */
					logger.debug("当前采用的分片模式为库内分片");
					params = manyTbshard(SQL, params, operation).toArray();
				}
				String NEW_SQL = params[0].toString();
				logger.info("sharding之后准备执行的数据库sql-->" + NEW_SQL);
			} else {
				/* 获取主/从的起始索引 */
				int index = WeightResolver.getIndex(kJdbcTemplate.getWr_weight(), operation);
				/* 切换数据源到master上 */
				setRoutingIndex(index);
			}
			try {
				/* 执行委托对象的目标方法 */
				result = proceedingJoinPoint.proceed(params);
				logger.debug("执行耗时->" + (System.currentTimeMillis() - befor) + "ms");
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 執行库内分片的分片模式
	 * 
	 * @author gaoxianglong
	 * 
	 * @param sql
	 *            需要进行重写的sql语句
	 * 
	 * @param params
	 *            委托对象的方法入参
	 * 
	 * @param operation
	 *            true为master启始索引，false为slave启始索引
	 * 
	 * @exception ShardException
	 * 
	 * @return List<Object> 重写后的委托对象的上下文信息
	 */
	protected List<Object> manyTbshard(String sql, Object[] params, boolean operation) {
		/* 获取配置文件中的分库分表条件 */
		String dbRuleArray = kJdbcTemplate.getDbRuleArray().toLowerCase().replaceAll("\\s", "");
		String tbRuleArray = kJdbcTemplate.getTbRuleArray().toLowerCase().replaceAll("\\s", "");
		/* 获取配置文件中分库分表的关键字 */
		List<String> keyNames = GetKeyName.getName(false, dbRuleArray, tbRuleArray);
		/* 解析分库分表条件 */
		long key = sqlParser.getKey(sql, keyNames);
		/* 获取数据库索引 */
		int dbIndex = dbRule.getIndex(key, dbRuleArray);
		/* 获取表索引 */
		int tbIndex = tbRule.getIndex(key, tbRuleArray);
		/* 解析配置文件中数据库和数据库表的数量 */
		String values[] = tbRuleArray.split("[\\%]");
		int tbSize = Integer.parseInt(values[1]);
		int dbSize = Integer.parseInt(values[2]);
		/* 持有真正的数据库表名的sql */
		String newSql = switchTab.setTabName(dbIndex, tbIndex, dbSize, tbSize, kJdbcTemplate.getConsistent(),
				sqlParser.getTbName(sql), sql);
		/* 获取获取master/slave的数据源启始索引 */
		int index = WeightResolver.getIndex(kJdbcTemplate.getWr_weight(), operation);
		dbIndex += index;
		/* 切换数据源 */
		setRoutingIndex(dbIndex);
		/* 重写委托对象的上下文信息，替换原先入参 */
		List<Object> newParams = Arrays.asList(params);
		newParams.set(0, newSql);
		return newParams;
	}

	/**
	 * 執行一库一片的分片模式
	 * 
	 * @author gaoxianglong
	 * 
	 * @param sql
	 *            需要进行重写的sql语句
	 * 
	 * @param params
	 *            委托对象的方法入参
	 * 
	 * @param operation
	 *            true为master启始索引，false为slave启始索引
	 * 
	 * @exception ShardException
	 * 
	 * @return List<Object> 重写后的委托对象的上下文信息
	 */
	protected List<Object> oneTbshard(String sql, Object[] params, boolean operation) {
		/* 获取配置文件中的分库分表条件 */
		String dbRuleArray = kJdbcTemplate.getDbRuleArray().toLowerCase().replaceAll("\\s", "");
		/* 获取配置文件中分库分表的关键字 */
		List<String> keyNames = GetKeyName.getName(true, dbRuleArray, null);
		/* 解析分库分表条件 */
		long key = sqlParser.getKey(sql, keyNames);
		/* 获取数据库索引 */
		int dbIndex = dbRule.getIndex(key, dbRuleArray);
		String newSql = null;
		if (kJdbcTemplate.getConsistent()) {
			/* 持有真正的数据库表名的sql */
			newSql = switchTab.setTabName(dbIndex, sqlParser.getTbName(sql), sql);
		} else {
			newSql = sql;
		}
		/* 获取获取master/slave的数据源启始索引 */
		int index = WeightResolver.getIndex(kJdbcTemplate.getWr_weight(), operation);
		dbIndex += index;
		/* 切换数据源 */
		setRoutingIndex(dbIndex);
		/* 重写委托对象的上下文信息，替换原先入参 */
		List<Object> newParams = Arrays.asList(params);
		newParams.set(0, newSql);
		return newParams;
	}

	/**
	 * 切换数据源路由索引
	 * 
	 * @author gaoxianglong
	 * 
	 * @param dbIndex
	 *            数据库索引
	 * 
	 * @return void
	 */
	private void setRoutingIndex(int dbIndex) {
		dataSourceHolder.setRoutingIndex(dbIndex);
		logger.info("kratos成功切换数据源,当前所持有的数据源索引为-->" + dbIndex);
	}
}
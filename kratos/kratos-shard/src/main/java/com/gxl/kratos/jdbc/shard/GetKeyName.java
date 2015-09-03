package com.gxl.kratos.jdbc.shard;

import java.util.Arrays;
import java.util.List;

import com.gxl.kratos.jdbc.exception.ShardException;

/**
 * 获取分库分表的关键字
 * 
 * @author gaoxianglong
 */
public abstract class GetKeyName {
	/**
	 * 解析并获取配置文件中的分库分表关键字
	 * 
	 * @author gaoxianglong
	 * 
	 * @param shardMode
	 *            分表模式,true为一库一片,false为库内分片
	 * 
	 * @param dbRuleArray
	 *            配置文件中的分库规则
	 * 
	 * @param tbRuleArray
	 *            配置文件中的分表规则
	 * 
	 * @exception ShardException
	 * 
	 * @return Set<String> 分库分表关键字
	 */
	public static List<String> getName(boolean shardMode, String dbRuleArray, String tbRuleArray) {
		List<String> keyNames = null;
		/* 验证分片模式 */
		if (shardMode) {
			if (validationOneTbRule(dbRuleArray)) {
				String dbKeyName = dbRuleArray.split("\\#")[1];
				keyNames = Arrays.asList(dbKeyName.split("\\|"));
			} else {
				throw new ShardException("分库分表规则配置信息有误");
			}
		} else {
			if (validationManyTbRule(dbRuleArray, tbRuleArray)) {
				String dbKeyName = dbRuleArray.split("\\#")[1];
				String tbKeyName = tbRuleArray.split("\\#")[1];
				if (dbKeyName.equals(tbKeyName)) {
					keyNames = Arrays.asList(dbKeyName.split("\\|"));
				} else {
					throw new ShardException("分库分表规则配置信息有误");
				}
			} else {
				throw new ShardException("分库分表规则配置信息有误");
			}
		}
		return keyNames;
	}

	/**
	 * 验证配置文件中库内分片模式的分库分表规则的配置信息是否有误
	 * 
	 * @author gaoxianglong
	 * 
	 * @param dbRuleArray
	 *            配置文件中的分库规则
	 * 
	 * @param tbRuleArray
	 *            配置文件中的分表规则
	 * 
	 * @return boolean 验证结果
	 */
	private static boolean validationManyTbRule(String dbRuleArray, String tbRuleArray) {
		boolean result = false;
		if (dbRuleArray.matches("[#][\\w|\\|]+[#][%][0-9]+[/][0-9]+")) {
			if (tbRuleArray.matches("[#][\\w|\\|]+[#][%][0-9]+[%][0-9]+")) {
				result = true;
			}
		}
		return result;
	}

	/**
	 * 验证配置文件中一库一片模式的分库分表规则的配置信息是否有误
	 * 
	 * @author gaoxianglong
	 * 
	 * @param dbRuleArray
	 *            配置文件中的分库规则
	 * 
	 * @return boolean 验证结果
	 */
	private static boolean validationOneTbRule(String dbRuleArray) {
		boolean result = false;
		if (dbRuleArray.matches("[#][\\w|\\|]+[#][%][0-9]+")) {
			result = true;
		}
		return result;
	}
}
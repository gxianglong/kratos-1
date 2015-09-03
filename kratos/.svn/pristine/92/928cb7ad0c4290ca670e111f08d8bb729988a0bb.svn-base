package com.gxl.kratos.jdbc.shard;

/**
 * 分库分表解析接口
 * 
 * @author gaoxianglong
 */
public interface ShardResolver {
	/**
	 * 获取分库索引/分表索引
	 * 
	 * @author gaoxianglong
	 * 
	 * @param key
	 *            分库分表条件
	 * 
	 * @param ruleArray
	 *            分库分表规则
	 * 
	 * @throws ShardException
	 * 
	 * @return int 分库索引/分表索引
	 */
	public int getIndex(long key, String ruleArray);
}
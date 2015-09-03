package com.gxl.kratos.jdbc.shard;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import com.gxl.kratos.jdbc.core.KratosJdbcTemplate;

/**
 * 解析分库规则后计算分库索引
 * 
 * @author gaoxianglong
 */
@Component
public class DbRule extends ShardResolverImpl {
	@Resource
	private KratosJdbcTemplate kJdbcTemplate;

	@Override
	public int getIndex(long key, String ruleArray) {
		int dbIndex = -1;
		/* 覆盖分库分表规则的具体值,比如"#userinfo_id# % dbSize",覆盖后为"#1000# % dbSize" */
		String cover = ruleArray.replace(ruleArray.split("\\#")[1], String.valueOf(key));
		if (kJdbcTemplate.getShardMode()) {
			/* 调用一库一片模式下配置文件中的分库规则 */
			dbIndex = dbResolver_oneTb(cover);
		} else {
			/* 调用库内分片模式下配置文件中的分库规则 */
			dbIndex = dbResolver(cover);
		}
		return dbIndex;
	}
}
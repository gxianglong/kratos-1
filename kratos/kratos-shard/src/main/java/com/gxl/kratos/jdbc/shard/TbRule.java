package com.gxl.kratos.jdbc.shard;

import org.springframework.stereotype.Component;

import com.gxl.kratos.jdbc.exception.ShardException;

/**
 * 解析分表规则后计算分表索引
 * 
 * @author gaoxianglong
 */
@Component
public class TbRule extends ShardResolverImpl {
	@Override
	public int getIndex(long key, String ruleArray) {
		/*
		 * 覆盖分库分表规则的具体值,比如"#userinfo_id# % tbSize % dbSize",覆盖后为
		 * "#1000# % tbSize % dbSize"
		 */
		String cover = ruleArray.replace(ruleArray.split("\\#")[1], String.valueOf(key));
		/* 调用解析分库规则 */
		return tbResolver(cover);
	}
}
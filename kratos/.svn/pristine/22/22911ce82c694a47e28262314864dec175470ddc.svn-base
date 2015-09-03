package com.gxl.kratos.jdbc.datasource.config;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.gxl.kratos.jdbc.core.KratosJdbcTemplate;

/**
 * kratos数据源路由实现，持有多数据源
 * 
 * 该数据源继承自Spring提供的AbstractRoutingDataSource，可以根据配置文件中的数据源key对多数据源进行动态切换，
 * 能够非常方便的实现数据源路由工作
 * 
 * @author gaoxianglong
 */
public class KratosDatasourceGroup extends AbstractRoutingDataSource implements DataSource {
	private Logger logger = LoggerFactory.getLogger(KratosDatasourceGroup.class);
	@Resource
	private DataSourceHolder dataSourceHolder;

	private KratosDatasourceGroup() {
		logger.info("动态数据源已经初始化完成...");
	}

	@Override
	protected Object determineCurrentLookupKey() {
		int index = -1;
		if (null != dataSourceHolder)
			index = dataSourceHolder.getRoutingIndex();
		return index;
	}
}
package com.gxl.kratos.jdbc.datasource.config;

import org.springframework.stereotype.Component;

/**
 * master数据源路由选择器
 * 
 * @author gaoxianglong
 */
@Component("dataSourceHolder")
public class KratosDataSourceHolder implements DataSourceHolder {
	@SuppressWarnings("unused")
	private static final ThreadLocal<Integer> holder;

	static {
		holder = new ThreadLocal<Integer>();
	}

	@Override
	public void setRoutingIndex(int index) {
		holder.set(index);
	}

	@Override
	public int getRoutingIndex() {
		return holder.get();
	}
}
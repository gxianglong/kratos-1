package com.gxl.kratos.jdbc.datasource.config;

/**
 * 数据源路由选择器接口
 * 
 * @author gaoxianglong
 */
public interface DataSourceHolder {
	/**
	 * 设置数据源路由索引
	 * 
	 * @author JohnGao
	 * 
	 * @param index
	 *            数据源路由索引
	 * 
	 * @return void
	 */
	public void setRoutingIndex(int index);

	/**
	 * 获取数据源路由索引
	 * 
	 * @author JohnGao
	 * 
	 * @return int 数据源路由索引
	 */
	public int getRoutingIndex();
}
package com.gxl.kratos.jdbc.core;

import com.gxl.kratos.jdbc.exception.WeightException;

/**
 * master/slave读写分离权重解析
 * 
 * @author gaoxianglong
 */
public abstract class WeightResolver {
	/**
	 * 通过配置的读写权重信息获取master/slave的数据源启始索引
	 * 
	 * @author gaoxianglong
	 * 
	 * @param wr_weight
	 *            读写权重比例，如比r0w1024
	 * 
	 * @param operation
	 *            true为master启始索引，false为slave启始索引
	 * 
	 * @throws WeightException
	 * 
	 * @return int 启始索引
	 */
	public static int getIndex(String wr_weight, boolean operation) {
		int index = -1;
		if (wr_weight.matches("[rR][0-9]+[wW][0-9]+")) {
			wr_weight = wr_weight.toUpperCase();
			String str = null;
			if (operation) {
				str = "W";
				index = Integer.parseInt(wr_weight.substring(wr_weight.indexOf(str) + 1));
			} else {
				str = "R";
				index = Integer.parseInt(wr_weight.substring(wr_weight.indexOf(str) + 1, wr_weight.indexOf("W")));
			}
		} else {
			throw new WeightException("master/slave读写分离权重配置信息有误");
		}
		return index;
	}
}
package com.gxl.kratos.utils.sequence;

/**
 * sequenceID的Manager类
 * 
 * @author gaoxianglong
 */
public class SequenceIDManger {
	/**
	 * 获取sequenceID
	 * 
	 * @author gaoxianglong
	 * 
	 * @param idcNum
	 *            IDC机房编码, 用于区分不同的IDC机房,1-3位长度
	 * 
	 * @param userType
	 *            类别,1-2位长度
	 * 
	 * @param memData
	 *            内存占位数量
	 * 
	 * @return long 返回生成的17-19位sequenceId
	 */
	public static long getSequenceId(int idcNum, int type, long memData) {
		return CreateSequenceIdService.createSequenceIdService().getSequenceId(idcNum, type, memData);
	}
}
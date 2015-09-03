package com.gxl.kratos.jdbc.exception;

/**
 * 分库分表异常
 * 
 * @author gaoxianglong
 */
public class ShardException extends KratosRuntimeException {
	private static final long serialVersionUID = -4815365861254362333L;

	public ShardException(String str) {
		super(str);
	}
}
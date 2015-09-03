package com.gxl.kratos.utils.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.gxl.kratos.utils.sequence.DbConnectionManager;
import com.gxl.kratos.utils.sequence.SequenceIDManger;

/**
 * 获取SequenceId测试类
 * 
 * @author gaoxianglong
 */
public class SequenceIdTest {
	static boolean c_result1 = false;
	static boolean c_result2 = false;

	/**
	 * 获取SequenceId
	 * 
	 * @author gaoxianglong
	 */
	public @Test void getSequenceId() {
		/* 初始化数据源信息 */
		DbConnectionManager.init("root", "88888888", "jdbc:mysql://114.215.110.169:3306/test", "com.mysql.jdbc.Driver");
		System.out.println(SequenceIDManger.getSequenceId(1, 1, 5000));
	}

	/**
	 * 批量获取SequenceId
	 * 
	 * @author gaoxianglong
	 */
	public @Test void getSequenceId2() {
		/* 初始化数据源信息 */
		DbConnectionManager.init("root", "88888888", "jdbc:mysql://114.215.110.169:3306/test", "com.mysql.jdbc.Driver");
		for (int i = 0; i < 1000; i++) {
			System.out.println(SequenceIDManger.getSequenceId(1, 1, 5000));
		}
	}

	/**
	 * 并发批量获取SequenceId
	 * 
	 * @author gaoxianglong
	 */
	public @Test void getSequenceId3() {
		/* 初始化数据源信息 */
		DbConnectionManager.init("root", "88888888", "jdbc:mysql://114.215.110.169:3306/test", "com.mysql.jdbc.Driver");
		final List<Long> id1 = new ArrayList<Long>();
		final List<Long> id2 = new ArrayList<Long>();
		final int size = 10000;
		new Thread() {
			public void run() {
				for (int i = 0; i < size; i++) {
					id1.add(SequenceIDManger.getSequenceId(1, 1, 5000));
				}
				SequenceIdTest.c_result1 = true;
			}
		}.start();
		new Thread() {
			public void run() {
				for (int i = 0; i < size; i++) {
					id2.add(SequenceIDManger.getSequenceId(1, 1, 5000));
				}
				SequenceIdTest.c_result2 = true;
			}
		}.start();
		for (;;) {
			if (c_result1 && c_result2) {
				if (id1.containsAll(id2)) {
					System.out.println("出现重复");
				} else {
					System.out.println("未出现重复");
				}
				break;
			}
			try {
				/* 休眠避免CPU虚高 */
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
package com.test.core;
/**
 * 执行接口
 * @author yuyilun
 *
 */
public interface Excutor {
	public <T> T query(String statement, Object parameter);
}

package com.mybatis.orm.core.bean;

/**
 * Function�������sql�����͡���������sql��䡢�������ͺͲ������͡�
 * @author yuyilun
 *
 */
public class Function {
	
	
	private String sqlType;
	private String funName;
	private String sql;
	private Object resutlType;
	private String parameterType;
	public String getSqlType() {
		return sqlType;
	}
	public void setSqlType(String sqlType) {
		this.sqlType = sqlType;
	}
	public String getFunName() {
		return funName;
	}
	public void setFunName(String funName) {
		this.funName = funName;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public Object getResutlType() {
		return resutlType;
	}
	public void setResutlType(Object resutlType) {
		this.resutlType = resutlType;
	}
	public String getParameterType() {
		return parameterType;
	}
	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}
	
	
	
	

}

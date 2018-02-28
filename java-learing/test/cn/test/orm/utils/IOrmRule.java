package cn.test.orm.utils;

public interface IOrmRule {
	
	public String class2TableName(String className);
	public String tableName2ClassName(String tableName);
	public String javaField2DbField(String javaField);
	public String dbField2JavaField(String dbField);
}

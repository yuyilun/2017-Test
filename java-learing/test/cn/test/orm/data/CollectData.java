package cn.test.orm.data;

import java.util.HashMap;
import java.util.Map;

import com.finallygo.build.pojo.MyTable;

public class CollectData {
	public static Map tableMap = new HashMap();
	public static Map foreignMap = new HashMap();
	public static Map insertFieldsCache = new HashMap();
	public static Map updateFieldsCache = new HashMap();
	public static Map deleteFieldsCache = new HashMap();
	public static Map selectFieldsCache = new HashMap();
	
	public static boolean isLazy = true;
	public static BuildMyTableForCommon builder = new BuildMyTableForCommon();
	
	static {
		if(!isLazy) {
			MyTable[] tables = builder.getMyTables();
			for(int i = 0; i< tables.length; i++) {
				tableMap.put(tables[i].getTableName().toUpperCase(), tables[i]);
			}
		}
		foreignMap = builder.getForeignKeys();
	}
	
	public static Map getForeignMap() {
		return foreignMap;
	}
	
	public void initMyTables() {
		
	}
	
	public static MyTable getTableByTableName(String tableName) {
		MyTable table = (MyTable) tableMap.get(tableName.toUpperCase());
		if(table != null) {
			return table;
		}
		//判断是否是懒加载
		if(!isLazy){//如果不是懒加载,又找不到就报错
			throw new RuntimeException("找不到该表名对应的表:"+tableName);
		}else{//如果是懒加载则取当前MyTable
			table=builder.getMyTable(tableName);
			if(table!=null){
				tableMap.put(table.getTableName().toUpperCase(), table);//放入缓存
				return table;
			}
			throw new RuntimeException("找不到该表名对应的表:"+tableName);
		}
		
		
	}
	
}

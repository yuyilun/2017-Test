package cn.test.orm.data;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.finallygo.build.pojo.MyField;
import com.finallygo.build.pojo.MyTable;
import com.finallygo.db.utils.ConnectHelper;
import com.finallygo.db.utils.DataBaseHelper;

public class BuildMyTableForCommon extends BaseBuildMyTable{

	public Map getForeignKeys() {
		
		Connection conn = ConnectHelper.getConn();
		Map foreignMap = new HashMap();
		ResultSet rs = null;
		
		try {
			DatabaseMetaData metaData = conn.getMetaData();
			rs = metaData.getTables(conn.getCatalog(), metaData.getUserName(), null, new String[] {"TABLE"});
			while(rs.next()) {
				foreignMap.putAll(this.getForeignKey(rs.getString("TABLE_NAME")));
			}
		}catch (SQLException e) {
			throw new RuntimeException("获取所有表的外键信息异常!",e);
		} finally{
			DataBaseHelper.closeRs(rs);
			DataBaseHelper.closeConn(conn);
		}
		return foreignMap;
	}
	
	public Map getForeignKey(String tableName) {
		Connection conn=ConnectHelper.getConn();
		Map foreignMap=new HashMap();
		ResultSet rs=null;
		try {
			DatabaseMetaData metaData=conn.getMetaData();
			rs=metaData.getImportedKeys(conn.getCatalog(), metaData.getUserName(), tableName);
			
			while(rs.next()){
				String pkTableName=rs.getString("PKTABLE_NAME");//主表名
				String pkColumnName=rs.getString("PKCOLUMN_NAME");//主表列
				String fkTableName=rs.getString("FKTABLE_NAME");//外表名
				String fkColumnName=rs.getString("FKCOLUMN_NAME");//外表列
				foreignMap.put((fkTableName+"."+fkColumnName).toUpperCase(), (pkTableName+"."+pkColumnName).toUpperCase());
			}
			
		} catch (SQLException e) {
			//这里出现的异常怎么办呢?
		} finally{
			DataBaseHelper.closeRs(rs);
			DataBaseHelper.closeConn(conn);
		}
		return foreignMap; 
	}
	
	public MyTable[] getMyTables() {
		Connection conn=ConnectHelper.getConn();
		ResultSet rs=null;
		List tables=new ArrayList();
		try {
			DatabaseMetaData metaData=conn.getMetaData();
			rs=metaData.getTables(conn.getCatalog(), metaData.getUserName(), null, new String[]{"TABLE"});
			while(rs.next()){
				MyTable table=this.getMyTable(rs.getString("TABLE_NAME"));
				tables.add(table);
			}
		} catch (Exception e) {
			throw new RuntimeException("将数据库的表转换为对象的时候出错",e);
		} finally{
			DataBaseHelper.closeRs(rs);
			DataBaseHelper.closeConn(conn);
		}
		MyTable[] tablesArray=new MyTable[tables.size()];
		tables.toArray(tablesArray);
		return tablesArray;
	}
	
	public MyTable getMyTable(String tableName) {
		MyTable table=new MyTable();
		table.setTableName(tableName);//设置表名
		MyField[] fields=this.getTableField(tableName);//得到表的属性数组
		table.setFields(fields);
		table.setInsertSql(this.buildInsertSql(tableName,fields));//设置插入语句(具体参考MyTable类)
		table.setUpdateSql(this.buildUpdateSql(tableName, fields));//设置更新语句
		table.setDeleteSql(this.buildDeleteSql(tableName, fields));//设置删除语句
		table.setSelectSql(this.buildSelectSql(tableName, fields));//设置选择语句
		return table;
		
		
	}

	public MyField[] getTableField(String tableName) {
		Connection conn = ConnectHelper.getConn();
		List fields = new ArrayList();
		ResultSet rs = null;
		ResultSet rs2 = null;
		
		try {
			DatabaseMetaData metaData = conn.getMetaData();
			rs = metaData.getColumns(conn.getCatalog(), metaData.getUserName(), tableName, null);
			while(rs.next()) {
				String columnName=rs.getString("COLUMN_NAME");
				int dataType=rs.getInt("DATA_TYPE");
				String typeName=rs.getString("TYPE_NAME");
				int columnSize=rs.getInt("COLUMN_SIZE");
				int decimalDigits=rs.getInt("DECIMAL_DIGITS");
				int nullable=rs.getInt("NULLABLE");
				String columnDef=rs.getString("COLUMN_DEF");
				MyField field=new MyField();
				field.setFieldName(columnName);
				field.setDataType(dataType);
				field.setDbFieldType(typeName);
				field.setFieldLength(columnSize);
				field.setScale(decimalDigits);
				field.setAllowNull(nullable==java.sql.DatabaseMetaData.columnNoNulls?false:true);
				field.setDefaultValue(columnDef);
				fields.add(field);
			}
			
			rs2 = metaData.getPrimaryKeys(conn.getCatalog(), metaData.getUserName(), tableName);
			List keys = new ArrayList();
			while(rs2.next()) {
				keys.add(rs2.getString("COLUMN_NAME"));
			}
			
			for(int i =0; i< fields.size(); i++) {
				MyField f=(MyField) fields.get(i);
				if(keys.contains(f.getFieldName())){
					f.setKey(true);
				}
			}
			
		} catch (Exception e) {
			throw new RuntimeException("将表"+tableName+"的列转换为Field对象的时候出错",e);
		} finally{
			DataBaseHelper.closeRs(rs);
			DataBaseHelper.closeRs(rs2);
			DataBaseHelper.closeConn(conn);
		}
		MyField[] fieldArray=new MyField[fields.size()];
		fields.toArray(fieldArray);
		return fieldArray;
	}

}
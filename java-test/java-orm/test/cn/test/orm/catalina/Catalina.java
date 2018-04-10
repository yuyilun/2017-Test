package cn.test.orm.catalina;

import java.util.Map;

import com.finallygo.db.utils.ConnectHelper;
import com.finallygo.db.utils.JdbcTemplate;

import cn.test.orm.domain.TbBook;
import cn.test.orm.sql.AnalyzeSqlXml;
import cn.test.orm.utils.OrmUtils;

public class Catalina {
	
	public static void main(String[] args) {
		testJdbcTemplateSelect();
		
	}

	private static void testJdbcTemplateSelect() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ConnectHelper.getConn(),true);
		String sql = AnalyzeSqlXml.getSql("selectBooks3");
		Map map = (Map)jdbcTemplate.executeQuery(sql, new Object[] {new Long(3)}).get(0);
		TbBook book = (TbBook)OrmUtils.map2ObjectForJdbcTemplate(map,TbBook.class);
		System.out.println(book.getBookName());
	}
	
	
	
	

}

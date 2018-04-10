package cn.test.orm.sql;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 解析sql的XML文件
 * @author xyd-yuyilun
 *
 */
public class AnalyzeSqlXml {
	private static Map sqlMap = new LinkedHashMap();
	private static DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	private static DocumentBuilder builder;
	private static ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	
	static {
		initSql();
	}

	public static void initSql() {
		try {
			builder = factory.newDocumentBuilder();
			URL url = classLoader.getResource("sql.xml");
			Document doc = builder.parse(url.toString());
			getSqlXmlObjects(doc);
			Document[] docs = getOtherSqlDocs(doc);
			for(int i = 0 ; i<docs.length;i++) {
				getSqlXmlObjects(docs[i]);
			}
		}catch (Exception e) {
			throw new RuntimeException("解析sql文件出现异常!",e);
		}
		
	}

	private static Document[] getOtherSqlDocs(Document doc) {
		NodeList nodeList = doc.getElementsByTagName("include");
		Document[] docs = new Document[nodeList.getLength()];
		for(int i=0;i<nodeList.getLength();i++) {
			Node node = nodeList.item(i);
			NamedNodeMap nodeMap = node.getAttributes();
			String fileName = nodeMap.getNamedItem("file").getNodeValue();
			try {
				builder= factory.newDocumentBuilder();
				URL url = classLoader.getResource(fileName);
				docs[i] = builder.parse(url.toString());
			}catch(Exception e) {
				throw new RuntimeException("在解析文件:"+fileName+"出现异常",e);
			}
		}
		return docs;
	}

	public static Map getSqlXmlObjects(Document doc) {
		NodeList nodeList = doc.getElementsByTagName("sql");
		
		for(int i = 0 ; i < nodeList.getLength() ; i++) {
			SqlXmlObject sqlXmlObject = new SqlXmlObject();
			Node node = nodeList.item(i);
			NamedNodeMap nodeMap = node.getAttributes();
			String sqlId = nodeMap.getNamedItem("id").getNodeValue();
			
			
			NodeList list = node.getChildNodes();
			String sqlContent = list.item(0).getNodeValue();
			if(sqlContent == null || "".equals(sqlContent.trim())) {
				if(list.getLength() > 0) {
					sqlContent = list.item(1).getNodeValue();
				}
				if(sqlContent == null || "".equals(sqlContent.trim())) {
					throw new RuntimeException("在sqlId为"+sqlId+"中没有找到有效的sql语句!");
				}
			}
			String[] sqls = sqlContent.trim().split("\n");
			StringBuffer newSql = new StringBuffer();
			for(int j=0 ; j<sqls.length; j++) {
				newSql.append(fiterSql(sqls[j]));
			}
			sqlXmlObject.setSqlId(sqlId);
			sqlXmlObject.setSqlContent(newSql.toString().trim());
			sqlMap.put(sqlId, sqlXmlObject);
		}
		return sqlMap;
	}
	
	/**
	 * 过滤sql语句,主要是去掉注释
	 * @param sql
	 * @return
	 */
	public static String fiterSql(String sql) {
		String newSql = sql;
		if(sql.indexOf("--") != -1) {
			newSql = sql.substring(0, sql.indexOf("--"));
		}
		return newSql;
	}
	
	public static Map getSqlMap() {
		return sqlMap;
	}
	
	public static String getSql(String sqlId) {
		SqlXmlObject sqlXmlObject = (SqlXmlObject)sqlMap.get(sqlId);
		if(sqlXmlObject == null) {
			throw new RuntimeException("没有找到sql编号为:"+sqlId+"的sql语句");
		}
		return sqlXmlObject.getSqlContent();
	}
	
}

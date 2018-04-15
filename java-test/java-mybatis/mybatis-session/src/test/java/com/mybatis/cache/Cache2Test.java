package com.mybatis.cache;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import com.test.dao.UserMapper;
import com.test.pojo.User;

public class Cache2Test {
	
	private SqlSessionFactory factory;
	
	@Before
	public void createSqlSessionFactory() throws Exception {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		factory = new SqlSessionFactoryBuilder().build(inputStream);
	}
	
	
	@Test
	public void testCache2() {
		
		SqlSession sqlSession1 = factory.openSession();
		SqlSession sqlSession2 = factory.openSession();
		SqlSession sqlSession3 = factory.openSession();
		
		UserMapper userMapper = sqlSession1.getMapper(UserMapper.class);
		
		User user = userMapper.selectByPrimaryKey(1);
		System.out.println(user);
		sqlSession1.close();
		
		
		UserMapper userMapper3 = sqlSession3.getMapper(UserMapper.class);
		User user3 = userMapper3.selectByPrimaryKey(1);
		user3.setName("css");
		userMapper3.updateByPrimaryKey(user3);
		
		sqlSession3.commit();
		sqlSession3.close();
		
		
		UserMapper userMapper2 = sqlSession2.getMapper(UserMapper.class);
		User user2 = userMapper2.selectByPrimaryKey(1);
		System.out.println(user2);
		
		sqlSession2.close();
		
		
	}
	
	
}

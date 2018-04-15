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

public class Cache1Test {
	
	private SqlSessionFactory factory;
	
	@Before
	public void createSqlSessionFactory() throws Exception {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		factory = new SqlSessionFactoryBuilder().build(inputStream);
	}
	
	
	@Test
	public void testCache1() {
		
		SqlSession sqlSession = factory.openSession();
		
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		
		User user = userMapper.selectByPrimaryKey(1);
		System.out.println(user);
		
		if(user == null) {
			user = new User();
			user.setId(1);
			user.setName("ces02");
			userMapper.insert(user);
		}else {
			user.setName("ces04");
			userMapper.updateByPrimaryKey(user);
		}
		System.out.println(user);
		//sqlSession.commit();
		
		
		User user2 = userMapper.selectByPrimaryKey(1);
		System.out.println(user2);
		
		sqlSession.close();
	}
	
	
}

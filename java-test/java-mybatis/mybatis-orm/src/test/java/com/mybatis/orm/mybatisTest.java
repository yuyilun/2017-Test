package com.mybatis.orm;

import org.junit.jupiter.api.Test;

import com.mybatis.orm.core.Sqlsession;
import com.mybatis.orm.mapper.UserMapper;
import com.mybatis.orm.pojo.User;

public class mybatisTest {
	
	  @Test
	  public void test1() {
	    	 Sqlsession sqlsession=new Sqlsession();  
	         UserMapper mapper = sqlsession.getMapper(UserMapper.class);  
	         User user = mapper.getUserById(1);
	         System.out.println(user.toString());
	    }

}	

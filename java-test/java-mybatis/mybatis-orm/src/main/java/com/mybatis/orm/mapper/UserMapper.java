package com.mybatis.orm.mapper;

import com.mybatis.orm.pojo.User;

public interface UserMapper {
	
	/**
	 * 查询用户信息
	 * @param id
	 * @return
	 */
	User getUserById(String id);
	

}

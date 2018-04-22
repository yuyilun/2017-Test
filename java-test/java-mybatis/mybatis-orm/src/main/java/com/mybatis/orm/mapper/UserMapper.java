package com.mybatis.orm.mapper;

import com.mybatis.orm.pojo.User;

public interface UserMapper {
	
	/**
	 * ��ѯ�û���Ϣ
	 * @param id
	 * @return
	 */
	User getUserById(int id);
	

}

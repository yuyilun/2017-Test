package cn.test.springmvc.service.impl;

import cn.test.springmvc.annotation.Service;
import cn.test.springmvc.service.ModifyService;
@Service
public class ModifyServiceImpl implements ModifyService {

	@Override
	public String add(String name, String addr) {
		return "invoke add name = " + name + " addr = " + addr;
	}

	@Override
	public String remove(Integer id) {
		 return "remove id = " + id;  
	}

}

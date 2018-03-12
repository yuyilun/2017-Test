package cn.test.springmvc.service.impl;

import cn.test.springmvc.annotation.Service;
import cn.test.springmvc.service.QueryService;
@Service("myQueryService")
public class QueryServiceImpl implements QueryService {

	@Override
	public String search(String name) {
		 return "invoke search name = " + name;  
	}

}

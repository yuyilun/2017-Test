package cn.test.springmvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.test.springmvc.annotation.Autowired;
import cn.test.springmvc.annotation.Controller;
import cn.test.springmvc.annotation.RequestMapping;
import cn.test.springmvc.annotation.RequestParam;
import cn.test.springmvc.service.ModifyService;
import cn.test.springmvc.service.QueryService;
import cn.test.springmvc.util.Tools;
/**
 * 控制层
 * @author xyd-yuyilun
 *
 */
@Controller
@RequestMapping("/web")
public class WebController {
	@Autowired
	private ModifyService modifyService;
	
	@Autowired("myQueryService")
	private QueryService queryService;
	
	@RequestMapping("/search")
	public void search(@RequestParam("name")String name, HttpServletRequest request, HttpServletResponse response) {
		String result = queryService.search(name);
		Tools.out(response,result); 
	}
	
	@RequestMapping("/add")
	public void add(@RequestParam("name")String name, @RequestParam("addr")String addr, HttpServletRequest request, HttpServletResponse response) {
		 String result = modifyService.add(name, addr);  
		 Tools.out(response,result);  
	}
	
	@RequestMapping("/remove")
	public void remove(@RequestParam("name") Integer id,  
            HttpServletRequest request, HttpServletResponse response) {  
			String result = modifyService.remove(id);  
			Tools.out(response,result); 
	}
	
	@RequestMapping("/update")  
    public void update(String name, boolean flag,  
                    HttpServletRequest request, HttpServletResponse response) {  
		Tools.out(response, "我是name：" + name + "flag为：" + flag);  
    } 
	
	
}

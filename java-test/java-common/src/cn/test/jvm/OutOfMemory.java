package cn.test.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * ²âÊÔÄÚ´æÒç³ö
 * @author xyd-yuyilun
 *
 */
public class OutOfMemory {
	
	static class OOMObject{}

	public static void main(String[] args) {
		List<OOMObject> list=new ArrayList<OOMObject>();
		
		while(true) {
			
			list.add(new OOMObject());
			
		}

	}

}

package cn.test.jvm;

import java.lang.reflect.Field;

import sun.misc.Unsafe;
/**
 * vm args:-Xmx20M -XX:MaxDirectMemorySize=10M
 * @author xyd-yuyilun
 *
 */
public class DiectMemoryOOM {
	
	private static final int _1MB = 1024 * 1024;
	
	public static void main(String[] args) throws Exception{
		Field unsafeField = Unsafe.class.getDeclaredFields()[0];
		unsafeField.setAccessible(true);
		
		Unsafe unsafe = (Unsafe)unsafeField.get(null);
		while(true) {
			unsafe.allocateMemory(_1MB);
			
		}
		
		
	}

}

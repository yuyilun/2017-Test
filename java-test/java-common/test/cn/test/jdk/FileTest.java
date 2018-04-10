package cn.test.jdk;

import static org.junit.Assert.assertEquals;
import java.io.File;

import org.junit.Test;

public class FileTest {
	
	
	@Test
	public void test() throws Exception {
		String userDir = System.getProperty("user.dir");
		File homeFile = null;
		System.out.println(userDir);
		File f = new File(userDir);
	    homeFile = f.getCanonicalFile();
	    
	    //╤оят
	    assertEquals(f,homeFile);
	    
	    System.out.println(f.getName());
	    System.out.println(homeFile.getName());
	    homeFile = f.getAbsoluteFile();
	    System.out.println(homeFile.getName());
	    
	  
	    
	}
	
	
}

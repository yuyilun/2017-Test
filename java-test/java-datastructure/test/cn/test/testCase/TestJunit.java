package cn.test.testCase;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestJunit {
	
	@Test
	public void testAdd() {
		String str = "Junit is workgin fine";
		assertEquals("Junit is workgin fine",str);
		
	}
	
	
}

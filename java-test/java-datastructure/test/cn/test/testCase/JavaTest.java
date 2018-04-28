package cn.test.testCase;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class JavaTest{
	
	protected int value1,value2;
	
	@Before
	public void setUp() {
		value1 = 3;
		value2 = 3;
	}
	
	@Test
	public void testAdd() {
		double result = value1 + value2;
		assertTrue(result == 6);
	}
	
}

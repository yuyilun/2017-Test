package cn.test.testCase;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class TestJunit2 extends TestCase {
	protected int fValue1;
	protected int fValue2;

	@Before
	public void setUp() {
		fValue1 = 2;
		fValue2 = 3;
	}

	@Test
	public void testAdd() {
		// count the number of test cases
		System.out.println("No of Test Case = " + this.countTestCases());

		// test getName
		String name = this.getName();
		System.out.println("Test Case Name = " + name);

		// test setName
		this.setName("testNewAdd");
		String newName = this.getName();
		System.out.println("Updated Test Case Name = " + newName);
		double a = fValue1 + fValue2;
		assertTrue(5 == a);
	}

	// tearDown used to close the connection or clean up activities
	public void tearDown() {
	}
}

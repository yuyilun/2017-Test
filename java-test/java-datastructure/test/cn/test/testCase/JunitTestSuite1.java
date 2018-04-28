package cn.test.testCase;

import cn.test.testSuite.TestJunit1;
import cn.test.testSuite.TestJunit2;
import junit.framework.TestResult;
import junit.framework.TestSuite;

public class JunitTestSuite1 {
	public static void main(String[] args) {
		
		TestSuite suite = new TestSuite(TestJunit1.class,TestJunit2.class);
		TestResult testResult = new TestResult();
		suite.run(testResult);
		
		System.out.println("Number of test cases = " + testResult.runCount());
		
		
		
	}
}

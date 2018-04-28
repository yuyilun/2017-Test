package cn.test.testCase;

import org.junit.Test;

import junit.framework.AssertionFailedError;
import junit.framework.TestResult;

public class TestJunit3 extends TestResult{

	public synchronized void addError(Test test, Throwable e) {
		super.addError((junit.framework.Test)test, e);
	}

	public synchronized void addFailure(Test test, AssertionFailedError e) {
		super.addFailure((junit.framework.Test)test, e);
	}

	@Override
	public synchronized void stop() {
		super.stop();
	}
	
	@Test
	public void testAdd() {
		
	}
	
	
	
}	

package cn.test.testCase;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
/**
 * <!--  <scope>test</scope> -->
 * 一定要注释掉，不然会报错，找不到类
 * @author yuyilun
 *
 */
public class TestRunner {
	
	public static void main(String[] args) {

		Result runClasses = JUnitCore.runClasses(TestJunit.class);
		for (Failure failure : runClasses.getFailures()) {
			System.out.println(failure.toString());
		}
		System.out.println(runClasses.wasSuccessful());

		

	}

}

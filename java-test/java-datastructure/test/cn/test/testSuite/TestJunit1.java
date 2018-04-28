package cn.test.testSuite;

import static org.junit.Assert.*;

import org.junit.Test;

import cn.test.tools.MessageUtil;

public class TestJunit1 {

	String message = "Robert";
	MessageUtil messageUtil = new MessageUtil(message);

	@Test
	public void testPrintMessage() {
		System.out.println("Inside testPrintMessage()");
		assertEquals(message, messageUtil.printMessage());
	}

}

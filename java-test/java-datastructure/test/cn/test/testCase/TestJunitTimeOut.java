package cn.test.testCase;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import cn.test.tools.MessageUtil;

public class TestJunitTimeOut {
	String message = "Robert";
	MessageUtil messageUtil = new MessageUtil(message);

	@Test(timeout = 2000)
	public void testPrintMessage() {
		String date = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm").format(new Date());
		System.out.println("Inside testPrintMessage() " + date);
		messageUtil.printMessage();
	}

	@Test
	public void testSalutationMessage() {
		String date = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm").format(new Date());
		System.out.println("Inside testSalutationMessage() " + date);
		message = "Robert";
		assertEquals(message, messageUtil.salutationMessage());
	}
}

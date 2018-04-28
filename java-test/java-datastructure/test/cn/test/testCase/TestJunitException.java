package cn.test.testCase;

import static org.junit.Assert.*;

import org.junit.Test;

import cn.test.tools.MessageUtil;

public class TestJunitException {
	 String message = "Robert";   
	   MessageUtil messageUtil = new MessageUtil(message);

	   @Test
	   public void testPrintMessage() { 
	      System.out.println("Inside testPrintMessage()");     
	      messageUtil.printMessage();     
	   }

	   @Test(expected =ArithmeticException.class)
	   public void testSalutationMessage() {
	      System.out.println("Inside testSalutationMessage()");
	      message = "Hi!" + "Robert";
	      assertEquals(message,messageUtil.salutationMessage());
	   }
}

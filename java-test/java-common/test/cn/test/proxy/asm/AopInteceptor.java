package cn.test.proxy.asm;

public class AopInteceptor {
	public static void before(){
        System.out.println(".......before().......");
    }

    public static void after(){
        System.out.println(".......after().......");
    }

}

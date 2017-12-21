package cn.test.proxy.asm;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.sun.xml.internal.ws.org.objectweb.asm.Type;

/*
 * 测试asm
 * 
 * 
 * 
 * 1、如果要生成方法的代码，需要先以visitCode开头，访问结束需要调用visitEnd方法；
2、方法都是在线程中执行的，每个线程有自己的虚拟机栈，这个栈与线程同时创建，用于存储栈帧。栈帧随着方法调用而创建，方法结束时销毁。每个栈帧都有自己的本地变量表、操作数栈和指向常量池的引用。
本地变量表和操作数栈的大小在编译期确定，在asm中可以通过visitMaxs来指定本地变量表与操作数栈的大小。visitFrame方法可以指定栈帧中的本地变量与操作数。
对本地变量和操作数栈的大小设置受ClassWriter的flag取值影响：
（1）new ClassWriter(0),表明需要手动计算栈帧大小、本地变量和操作数栈的大小；
（2）new ClassWriter(ClassWriter.COMPUTE_MAXS)需要自己计算栈帧大小，但本地变量与操作数已自动计算好，当然也可以调用visitMaxs方法，只不过不起作用，参数会被忽略；
（3）new ClassWriter(ClassWriter.COMPUTE_FRAMES)栈帧本地变量和操作数栈都自动计算，不需要调用visitFrame和visitMaxs方法，即使调用也会被忽略。
这些选项非常方便，但会有一定的开销，使用COMPUTE_MAXS会慢10%，使用COMPUTE_FRAMES会慢2倍。
3、visitInsn、visitVarInsn、visitMethodInsn等以Insn结尾的方法可以添加方法实现的字节码。

 */
public class Generator {
	
	@Test
	public void testClass() throws Exception {
		//读取类文件
		ClassReader cr = new ClassReader("cn.test.proxy.asm.Account");
		//写类文件
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		
		//设置visitor
		CRClassVisitor classVisitor=new CRClassVisitor(Opcodes.ASM5,cw);
		cr.accept(classVisitor, ClassReader.SKIP_DEBUG);
		
		byte[] bs = cw.toByteArray();
		File file = new File("target/classes/cn/test/proxy/asm/Account.class"); 
		FileOutputStream fout = new FileOutputStream(file); 
		fout.write(bs); 
		fout.close();
		
		 Account account = new Account(); 
		/* account.operation(); */
		
	}
	
	public class CRClassVisitor extends ClassVisitor{

		public CRClassVisitor(int api,ClassVisitor cv) {
			super(api,cv);
		}
		
		public MethodVisitor visitMethod(int access, String name, String desc,
		            String signature, String[] exceptions) {
			MethodVisitor mv = cv.visitMethod(access, name, desc, signature,exceptions);
	        MethodVisitor wrappedMv = mv; 
		        if (cv != null) {
		            //return cv.visitMethod(access, name, desc, signature, exceptions);
		        	wrappedMv= new CRMethodVisitor(Opcodes.ASM5,mv);
		        }
		        return wrappedMv;
		}
		
		
	}
	
	
	public class CRMethodVisitor extends MethodVisitor{

		public CRMethodVisitor(int api,MethodVisitor mv) {
			super(api,mv);
		}
		
		
		public void visitCode() { 
			//先获取对象
			visitMethodInsn(Opcodes.INVOKESTATIC, Type.getInternalName(SecurityChecker.class), 
				"checkSecurity", "()V",false);
		 } 
		
	}
	
	
	/**
	 * 生成一个类，添加了一个方法
	 * @throws Exception
	 */
	@Test
	public void test() throws Exception {
		String className = "cn.test.proxy.asm.Account";  
        byte[] classData = createVoidMethod(className, "This is my first ASM test");  
        Class<?> clazz = new MyClassLoader().defineClassForName(className, classData);  
        clazz.getMethods()[0].invoke(clazz.newInstance()); 
	}
	
	/**
	 * 动态创建一个类，有一个无参数的构造函数 
	 * @param className
	 * @return
	 */
	static ClassWriter createClassWriter(String className)  {
		
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		/**
		 * 声明一个类，使用JDK1.8版本，public的类，父类是java.lang.Object，没有实现任何接口  
		 * 1:JDK1.8版本
		 * 2:public的类
		 * 3:类名
		 * 4：证书，无
		 * 5：父类
		 * 6：实现接口，无
		 */
		cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, className, null
				, "java/lang/Object", null);
		/**
		 * 初始化一个无参的构造函数 
		 * 1:public的方法
		 * 2:方法名
		 * 3：方法描述
		 * 4：签名
		 * 5：异常
		 */
		MethodVisitor constructor = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
		constructor.visitVarInsn(Opcodes.ALOAD, 0);//?
		//执行父类的init初始化 
		constructor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);		
		//从当前方法返回
		constructor.visitInsn(Opcodes.RETURN);
		constructor.visitMaxs(1, 1);
		constructor.visitEnd();
		
		return cw;
	}
	
	
	 /** 
     * 创建一个run方法，里面只有一个输出 
     * public void run() 
     * { 
     *      System.out.println(message); 
     * } 
     * @return 
     * @throws Exception 
     */  
    static byte[] createVoidMethod(String className, String message) throws Exception  
    {  
        //注意，这里需要把classname里面的.改成/，如com.asm.Test改成com/asm/Test  
        ClassWriter cw = createClassWriter(className.replace('.', '/'));  
        /**
         * 创建operation方法，()V表示函数，无参数，无返回值  
         * 1:Opcodes.ACC_PUBLIC,方法权限
         * 2:operation,方法名
         * 3：()V，方法描述
         * 4：签名，null
         * 5:异常，null
         */
        MethodVisitor runMethod = cw.visitMethod(Opcodes.ACC_PUBLIC, "operation",
        		"()V", null, null);  
        /**
         * System.out.println();
         * 先获取一个java.io.PrintStream对象，System.out
         * 1:Opcodes.GETSTATIC,静态对象System,对象类型
         * 2:java/lang/System，对象名字，owner，the internal name of the field's owner class 
         * 3：out，the field's name
         * 4：Ljava/io/PrintStream，the field's descriptor 
         */
        runMethod.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", 
        		"out", "Ljava/io/PrintStream;");  
        
        /**
         * 设置输出的内容
         * 将int, float或String型常量值从常量池中推送至栈顶 
         * (此处将message字符串从常量池中推送至栈顶[输出的内容])
         */
        runMethod.visitLdcInsn(message); 
        
        /**System.out.println();
         * 执行println方法（执行的是参数为字符串，无返回值的println函数）  
         * opcode:INVOKEVIRTUAL(虚拟调用), INVOKESPECIAL（特殊调用，调用父类方法）, INVOKESTATIC（静态方法调用） or INVOKEINTERFACE（接口方法调用）.
         * owner ：the internal name of the method's owner class (java/io/PrintStream).
         * name ：the method's name（println）
         * desc ：the method's descriptor ((Ljava/lang/String;)V).
         * itf if the method's owner class is an interface.（false）
         */
        runMethod.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream",
        		"println", "(Ljava/lang/String;)V", false); 
        
        //从当前方法返回
        runMethod.visitInsn(Opcodes.RETURN);  
        runMethod.visitMaxs(1, 1);  
        runMethod.visitEnd();  
          
        return cw.toByteArray();  
    }
}

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
 * ����asm
 * 
 * 
 * 
 * 1�����Ҫ���ɷ����Ĵ��룬��Ҫ����visitCode��ͷ�����ʽ�����Ҫ����visitEnd������
2�������������߳���ִ�еģ�ÿ���߳����Լ��������ջ�����ջ���߳�ͬʱ���������ڴ洢ջ֡��ջ֡���ŷ������ö���������������ʱ���١�ÿ��ջ֡�����Լ��ı��ر�����������ջ��ָ�����ص����á�
���ر�����Ͳ�����ջ�Ĵ�С�ڱ�����ȷ������asm�п���ͨ��visitMaxs��ָ�����ر������������ջ�Ĵ�С��visitFrame��������ָ��ջ֡�еı��ر������������
�Ա��ر����Ͳ�����ջ�Ĵ�С������ClassWriter��flagȡֵӰ�죺
��1��new ClassWriter(0),������Ҫ�ֶ�����ջ֡��С�����ر����Ͳ�����ջ�Ĵ�С��
��2��new ClassWriter(ClassWriter.COMPUTE_MAXS)��Ҫ�Լ�����ջ֡��С�������ر�������������Զ�����ã���ȻҲ���Ե���visitMaxs������ֻ�����������ã������ᱻ���ԣ�
��3��new ClassWriter(ClassWriter.COMPUTE_FRAMES)ջ֡���ر����Ͳ�����ջ���Զ����㣬����Ҫ����visitFrame��visitMaxs��������ʹ����Ҳ�ᱻ���ԡ�
��Щѡ��ǳ����㣬������һ���Ŀ�����ʹ��COMPUTE_MAXS����10%��ʹ��COMPUTE_FRAMES����2����
3��visitInsn��visitVarInsn��visitMethodInsn����Insn��β�ķ���������ӷ���ʵ�ֵ��ֽ��롣

 */
public class Generator {
	
	@Test
	public void testClass() throws Exception {
		//��ȡ���ļ�
		ClassReader cr = new ClassReader("cn.test.proxy.asm.Account");
		//д���ļ�
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		
		//����visitor
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
			//�Ȼ�ȡ����
			visitMethodInsn(Opcodes.INVOKESTATIC, Type.getInternalName(SecurityChecker.class), 
				"checkSecurity", "()V",false);
		 } 
		
	}
	
	
	/**
	 * ����һ���࣬�����һ������
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
	 * ��̬����һ���࣬��һ���޲����Ĺ��캯�� 
	 * @param className
	 * @return
	 */
	static ClassWriter createClassWriter(String className)  {
		
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		/**
		 * ����һ���࣬ʹ��JDK1.8�汾��public���࣬������java.lang.Object��û��ʵ���κνӿ�  
		 * 1:JDK1.8�汾
		 * 2:public����
		 * 3:����
		 * 4��֤�飬��
		 * 5������
		 * 6��ʵ�ֽӿڣ���
		 */
		cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, className, null
				, "java/lang/Object", null);
		/**
		 * ��ʼ��һ���޲εĹ��캯�� 
		 * 1:public�ķ���
		 * 2:������
		 * 3����������
		 * 4��ǩ��
		 * 5���쳣
		 */
		MethodVisitor constructor = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
		constructor.visitVarInsn(Opcodes.ALOAD, 0);//?
		//ִ�и����init��ʼ�� 
		constructor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);		
		//�ӵ�ǰ��������
		constructor.visitInsn(Opcodes.RETURN);
		constructor.visitMaxs(1, 1);
		constructor.visitEnd();
		
		return cw;
	}
	
	
	 /** 
     * ����һ��run����������ֻ��һ����� 
     * public void run() 
     * { 
     *      System.out.println(message); 
     * } 
     * @return 
     * @throws Exception 
     */  
    static byte[] createVoidMethod(String className, String message) throws Exception  
    {  
        //ע�⣬������Ҫ��classname�����.�ĳ�/����com.asm.Test�ĳ�com/asm/Test  
        ClassWriter cw = createClassWriter(className.replace('.', '/'));  
        /**
         * ����operation������()V��ʾ�������޲������޷���ֵ  
         * 1:Opcodes.ACC_PUBLIC,����Ȩ��
         * 2:operation,������
         * 3��()V����������
         * 4��ǩ����null
         * 5:�쳣��null
         */
        MethodVisitor runMethod = cw.visitMethod(Opcodes.ACC_PUBLIC, "operation",
        		"()V", null, null);  
        /**
         * System.out.println();
         * �Ȼ�ȡһ��java.io.PrintStream����System.out
         * 1:Opcodes.GETSTATIC,��̬����System,��������
         * 2:java/lang/System���������֣�owner��the internal name of the field's owner class 
         * 3��out��the field's name
         * 4��Ljava/io/PrintStream��the field's descriptor 
         */
        runMethod.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", 
        		"out", "Ljava/io/PrintStream;");  
        
        /**
         * �������������
         * ��int, float��String�ͳ���ֵ�ӳ�������������ջ�� 
         * (�˴���message�ַ����ӳ�������������ջ��[���������])
         */
        runMethod.visitLdcInsn(message); 
        
        /**System.out.println();
         * ִ��println������ִ�е��ǲ���Ϊ�ַ������޷���ֵ��println������  
         * opcode:INVOKEVIRTUAL(�������), INVOKESPECIAL��������ã����ø��෽����, INVOKESTATIC����̬�������ã� or INVOKEINTERFACE���ӿڷ������ã�.
         * owner ��the internal name of the method's owner class (java/io/PrintStream).
         * name ��the method's name��println��
         * desc ��the method's descriptor ((Ljava/lang/String;)V).
         * itf if the method's owner class is an interface.��false��
         */
        runMethod.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream",
        		"println", "(Ljava/lang/String;)V", false); 
        
        //�ӵ�ǰ��������
        runMethod.visitInsn(Opcodes.RETURN);  
        runMethod.visitMaxs(1, 1);  
        runMethod.visitEnd();  
          
        return cw.toByteArray();  
    }
}

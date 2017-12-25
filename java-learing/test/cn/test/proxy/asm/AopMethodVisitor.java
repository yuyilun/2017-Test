package cn.test.proxy.asm;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class AopMethodVisitor extends MethodVisitor implements Opcodes{

	public AopMethodVisitor(int api,MethodVisitor mv) {
		super(api,mv);
	}
	
	@Override
    public void visitCode() {
        super.visitCode();
        this.visitMethodInsn(INVOKESTATIC, "cn/test/proxy/asm/AopInteceptor", "before", "()V", false);
    }

    @Override
    public void visitInsn(int opcode) {
        if (opcode >= IRETURN && opcode <= RETURN)// �ڷ���֮ǰ����after ���롣
            this.visitMethodInsn(INVOKESTATIC, "cn/test/proxy/asm/AopInteceptor", "after", "()V", false);
        super.visitInsn(opcode);
    }

	
}

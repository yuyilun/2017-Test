package cn.test.jvm;

/**
 * 
 * @author xyd-yuyilun
 *
 */
public class JavaVMStackOOM {
	
	private void dontStop() {
		while(true){}
	}
	
	public void stackLeakByThread() {
		
		while(true) {
			Thread th=new Thread(new Runnable(){
					@Override
					public void run() {
						dontStop();
					}
				});
			th.start();
		}
	}
	
	
	public static void main(String[] args) {
		JavaVMStackOOM jv=new JavaVMStackOOM();
		jv.stackLeakByThread();

	}

}

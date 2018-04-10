package cn.test.proxy;

public interface Calculator {
	public int a=0;

	public int add(int a ,int b);
	
	public int reduce(int a,int b);
	
	public void lazyAction(int a,int b);
	
	public static class CalculatorImpl implements Calculator{

		@Override
		public int add(int a, int b) {
			System.out.println("doing add");
			return a+b;
		}

		@Override
		public int reduce(int a, int b) {
			return a-b;
		}

		@Override
		public void lazyAction(int a, int b) {
			
		}
	}
	
	public static class CalculatorProxy implements Calculator{
		
		private Calculator cl;
		public CalculatorProxy(Calculator cl) {
			this.cl=cl;
		}
		
		@Override
		public int add(int a, int b) {
			 //ִ��һЩ����
            System.out.println("begin ");
            int result = cl.add(a, b);
            System.out.println("end ");
            return result;
		}

		@Override
		public int reduce(int a, int b) {
			return 0;
		}

		@Override
		public void lazyAction(int a, int b) {
			
		}
		
		
		
		
	}
	
	
}

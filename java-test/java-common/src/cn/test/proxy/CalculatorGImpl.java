package cn.test.proxy;

public class CalculatorGImpl extends AbstractCalculator implements Calculator{

	private int j = 10;
	@Override
	public int add(int a, int b) {
		//j=new Random().nextInt();
		if(a < 100) {
			a=100+j;
			j++;
		}
		if(b>100) {
			b=100+j;
		}
		
		return a+b;
	}
	@Override
	public int reduce(int a,int b) {
		if((a-b) >0) {
			j++;
		}
		if((a-b) <= 0) {
			j--;
		}
		return j;
	}
	@Override
	public void lazyAction(int a, int b) {
		System.out.println("lazy action");
	}
	
	
	
	
}

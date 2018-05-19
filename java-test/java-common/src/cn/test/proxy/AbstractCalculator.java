package cn.test.proxy;

public class AbstractCalculator {

	public OBean obean;
	
	public AbstractCalculator() {
		OBean obean=new OBean();
		obean.setAge(8);
		obean.setName("Good");
		this.obean=obean;
	}

	public int add(int a, int b) {
		return a + b;
	}

	public int reduce(int a, int b) {
		return a - b;
	}

	public OBean getObean() {
		return obean;
	}

	public void setObean(OBean obean) {
		this.obean = obean;
	}
	
}

package cn.test.tree;

/**
 * ���Ľڵ㣺 ��ǰ�ڵ㣬���ӽڵ㣬���ӽڵ�
 * @author xyd-yuyilun
 *
 */
public class Node {
	int value;
	Node leftChild;
	Node rightChild;
	
	Node(int value){
		this.value=value;
	}
	
	public void disPlay() {
		System.out.println(this.value+"\t");
	}
	
	@Override  
    public String toString() {  
        return String.valueOf(value);  
    }  
	
}

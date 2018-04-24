package cn.test.tree;

/**
 * 树的节点： 当前节点，左子节点，右子节点
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

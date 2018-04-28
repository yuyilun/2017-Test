package cn.test.tree;
/**
 * 红黑树：节点元素可比较
 *  是一种自平衡二叉查找树，是在计算机科学中用到的一种数据结构，典型的用途是实现关联数组。
 *  红黑树和AVL树类似，都是在进行插入和删除操作时通过特定操作保持二叉查找树的平衡，从而获得较高的查找性能。
 *  https://baike.baidu.com/item/%E7%BA%A2%E9%BB%91%E6%A0%91/2413209
 *  
 *  性质1. 节点是红色或黑色。
	性质2. 根节点是黑色。
	性质3 每个叶节点（NIL节点，空节点）是黑色的。
	性质4 每个红色节点的两个子节点都是黑色。(从每个叶子到根的所有路径上不能有两个连续的红色节点)
	性质5. 从任一节点到其每个叶子的所有路径都包含相同数目的黑色节点。
 * 
 *  https://blog.csdn.net/eson_15/article/details/51144079
 * 
 * @author yuyilun
 */
public class RedBlackTree<T extends Comparable<T>> {
	
	
	private Node<T> root;
	
	private static final boolean RED = false; //定义红黑树标志  
	private static final boolean BLACK = true;  
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public class Node<T extends Comparable<T>>{
		T obj;
		Node<T> left,right;
		Node<T> parent;
		boolean color;
		
		public Node(Node<T> left,T obj,Node<T> right,Node<T> parent,boolean color){
			this.obj = obj;
			this.left = left;
			this.right = right;
			this.parent = parent;
			this.color = color;
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

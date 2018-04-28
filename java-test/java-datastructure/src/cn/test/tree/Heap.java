package cn.test.tree;

import java.util.Arrays;

/**
 *  堆的实现
 *  任意节点小于（或大于）它的所有后裔，最小元（或最大元）在堆的根上（堆序性）。
	堆总是一棵完全树。即除了最底层，其他层的节点都被元素填满，且最底层尽可能地从左到右填入。 
	将根节点最大的堆叫做最大堆或大根堆，根节点最小的堆叫做最小堆或小根堆。常见的堆有二叉堆、斐波那契堆等。
	
	堆的主要应用场景有：堆排序以及优先队列
	
	对于理解堆以及实现堆很重要的一点就是明白堆的表现形式，堆是树的一种，所以很自然的想到使用链表来实现堆，
	其实不然，由于我们需要频繁的对堆进行增加删除，所以一般堆的底层都是通过数组来实现，那么就有一个问题：
	数组如何表现出堆的结构呢？这里就有一个规则，即数组的第一个元素（即下标为0的元素）为堆的根节点，
	其他节点按照堆结构自上而下，自左而右依次表示为数组中的元素，这是一种既非前序也非后序，
	更非中序的遍历树的方式。
	
	https://blog.csdn.net/vickyway/article/details/49925367
	
 * @author yuyilun
 *
 */
@SuppressWarnings("rawtypes")
public abstract class Heap<T> {

	protected transient Object[] data;
	protected int length = 0;
	
	/*public Heap() {}
	
	public Heap(T[] data) {
		this.data = data;
		this.length = data.length;
	}*/

	/**
	 * 构建堆
	 */
	
	public abstract Heap buildHeap();

	/**
	 * 删除一个节点，只能删除根节点
	 * 
	 * @return
	 */
	public abstract Heap remove();

	/**
	 * 插入一个节点，只能插入到最后
	 * 
	 * @param value
	 * @return
	 */
	public abstract Heap insert(T value);

	/**
	 * 从node开始自上而下调整堆
	 * 
	 * @param node
	 */
	public abstract void adjustDownHeap(int length);

	/**
	 * 从node开始自下而上调整堆
	 * 
	 * @param node
	 */
	public abstract void adjustUpHeap(int length);

	/**
	 * 交换元素
	 * @param n1
	 * @param n2
	 */
	@SuppressWarnings("unchecked")
	public void swap(int n1, int n2) {
		T temp = (T) data[n1];
		data[n1] = data[n2];
		data[n2] = temp;
	}
	
	/**
	 * 获取父节点的下标
	 * @param node
	 * @return
	 */
	protected int getParentIndex(int node) {
		return (node - 1) >> 1;
	}
	
	/**
	 * 获取右子节点的下标
	 * @param node
	 * @return
	 */
	protected int getRightChildIndex(int node) {
		return (node << 1 ) + 2;
	}
	
	/**
	 * 获取左子节点的下标
	 * @param node
	 * @return
	 */
	protected int getLeftChildIndex(int node) {
		return (node << 1) + 1;
	}
	
	 /**
     * 打印堆
     */
    protected void print() {
        System.out.println(Arrays.toString(data));
    }
    
}




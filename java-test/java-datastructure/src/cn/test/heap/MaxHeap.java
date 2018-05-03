package cn.test.heap;

import java.util.Comparator;

public class MaxHeap<T> extends Heap<T>{
	
	private final Comparator<? super T> comparator;
	
	public MaxHeap() {
		this.comparator = null;
	}
	
	public MaxHeap(Comparator<? super T> comparator) {
		this.comparator = comparator;
	}
	

	@SuppressWarnings("rawtypes")
	@Override
	public Heap buildHeap() {
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Heap remove() {
		if(length == 0) {
			return this;
		}
		//在表尾插入数据元素，在表头删除数据元素,满足“First In First Out”。
		swap(0,length - 1);
		Object[] newData = new Object[length - 1];
		System.arraycopy(data, 0, newData, 0, length - 1);
		this.data = newData;
		this.length = length - 1;
		adjustDownHeap(0);
		return this;
	}
	
	
	@SuppressWarnings("rawtypes")
	@Override
	public Heap insert(T value) {
		Object[] newData = new Object[length + 1];
		if(length > 0) {
			System.arraycopy(data, 0, newData, 0, length);
		}
		newData[length] = value;
		this.data = newData;
		this.length = length + 1;
		adjustUpHeap(this.length - 1);
		return this;
	}
	
	/**
	 * 递归
	 * 从上往下调整 
	 */
	@Override
	public void adjustDownHeap(int node) {
		
		int rightChild = getRightChildIndex(node);
		int leftChild = getLeftChildIndex(node);
		
		int max = node;
		
		if(rightChild < length && compare(rightChild,max) > 0) {
			max = rightChild;
		}
		
		if(leftChild < length && compare(leftChild, max) > 0) {
			max = leftChild;
		}
		
		if(max != node) {
			swap(node, max);
			adjustDownHeap(max);
		}
	}
	
	
	
	@SuppressWarnings("unchecked")
	public int compare(int node1,int node2) {
		return comparator != null ? comparator.compare((T)data[node1],(T)data[node2])
				:((Comparable<? super T>) data[node1]).compareTo((T)data[node2]);
	}
	
	/**
	 * 递归
	 * 从下往上调整
	 */
	@Override
	public void adjustUpHeap(int node) {
		int parent = getParentIndex(node);
		if(parent >= 0 && compare(parent,node) < 0) {
			swap(node, parent);
			adjustUpHeap(parent);
		}
		
	}


	
}

package cn.test.tree;

import java.util.Arrays;

import org.junit.Test;

public class MaxHeapTest {
	@Test
	public void test(){
		MaxHeap<Integer> heap = new MaxHeap<Integer>();
		int[] data = new int[10];
        for (int i = 0; i < data.length; i++) {
            data[i] = (int) (Math.random() * 100);
            heap.insert(data[i]);
        }
        System.out.println("插入MaxHeap之前："+Arrays.toString(data));
        System.out.println("插入MaxHeap之后");
        heap.print();
        System.out.println("MaxHeap移除根之后");
        heap.remove().print();
        System.out.println("MaxHeap插入一个之后");
        heap.insert((int) (Math.random() * 100)).print();
	}

}

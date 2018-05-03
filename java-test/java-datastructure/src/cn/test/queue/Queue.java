package cn.test.queue;

/**
 * 
 *  了解完循环顺序队列和链式队列的实现后，我们最后再来了解一个特殊的队列，也就是优先队列，在某些情况下，有些应用系统要求不仅需要按照“先来先服务”的原则进行，而且还需按照任务的重要或紧急程度进行排队处理，此时就需要使用到优先队列。
 *  比如在操作系统中进行进程调度管理，每个进程都具备一个优先级值以表示进程的紧急程度，优先级高的进行先执行，同等级进程按照先进先出的原则排队处理，此时操作系统使用的便是优先队列管理和调度进程。 
      优先级队列也是一种特殊的数据结构，队列中的每个元素都有一个优先级，若每次出队的是具有最高优先级的元素，则称为降序优先级队列(总是先删除最大的元素)。
	若每次出队的是值最小的元素，则称为升序优先级队列(总是先删除最小的元素)，通常情况下我们所说的优先队列，一般是指降序优先级队列。关于优先队列的实现，可以使用有序数组或者有序链表，也可以使用二叉树（二叉堆）实现，这里我们仅给出有序链表的简单实现方案。而二叉树的实现，留着后面我们分析完树时再给出。好~，这里使用之前分析过的MyLikedList作为基底，实现一个排序的SortLinkedList继承自MyLinkedList，这里需要注意的是排序链表中的T类型必须是实现了Comparable接口的类型，在SortLinkedList中主要重写添加的add方法，插入逻辑是，通过比较元素的大小加入，而非简单下标或尾部插入
 * 
 * https://blog.csdn.net/javazejian/article/details/53375004
 * @author Administrator
 *
 * @param <T>
 */
public interface Queue<T> {
	/**
	 * 返回队列长度
	 * 
	 * @return
	 */
	int size();

	/**
	 * 判断队列是否为空
	 * 
	 * @return
	 */
	boolean isEmpty();

	/**
	 * data 入队,添加成功返回true,否则返回false,可扩容
	 * 
	 * @param data
	 * @return
	 */
	boolean add(T data);

	/**
	 * offer 方法可插入一个元素,这与add 方法不同， 该方法只能通过抛出未经检查的异常使添加元素失败。
	 * 而不是出现异常的情况，例如在容量固定（有界）的队列中 NullPointerException:data==null时抛出
	 * 
	 * @param data
	 * @return
	 */
	boolean offer(T data);

	/**
	 * 返回队头元素,不执行删除操作,若队列为空,返回null
	 * 
	 * @return
	 */
	T peek();

	/**
	 * 返回队头元素,不执行删除操作,若队列为空,抛出异常:NoSuchElementException
	 * 
	 * @return
	 */
	T element();

	/**
	 * 出队,执行删除操作,返回队头元素,若队列为空,返回null
	 * 
	 * @return
	 */
	T poll();

	/**
	 * 出队,执行删除操作,若队列为空,抛出异常:NoSuchElementException
	 * 
	 * @return
	 */
	T remove();

	/**
	 * 清空队列
	 */
	void clearQueue();

}

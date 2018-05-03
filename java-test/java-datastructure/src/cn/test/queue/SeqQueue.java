package cn.test.queue;

import java.io.Serializable;
import java.util.NoSuchElementException;

public class SeqQueue<T> implements Queue<T>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2195760067904788795L;
	@SuppressWarnings("unused")
	private static final int DEFAULT_SIZE = 10;

	private transient T elementData[];
	private transient int front, rear;
	private transient int size;
	private transient int modCount;

	@SuppressWarnings("unchecked")
	public SeqQueue(int capacity) {
		elementData = (T[]) new Object[capacity];
		front = rear = 0;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return front == rear;
	}

	@Override
	public boolean add(T data) {
		if (this.front == ((this.rear + 1) % this.size)) {
			ensureCapacity(elementData.length << 1 + 1);
		}
		elementData[this.rear] = data;
		this.rear = (this.rear + 1) % elementData.length;
		size++;
		modCount++;
		return true;
	}

	@SuppressWarnings("unchecked")
	private void ensureCapacity(int capacity) {

		if (capacity < size) {
			return;
		}

		T[] old = elementData;
		elementData = (T[]) new Object[capacity];
		int j = 0;
		for (int i = this.front; i != this.rear; i = (i + 1) % old.length) {
			elementData[j++] = old[i];
		}

		this.front = 0;
		this.rear = j;

	}

	/**
	 * offer 方法可插入一个元素,这与add 方法不同， 该方法只能通过抛出未经检查的异常使添加元素失败。
	 * 而不是出现异常的情况，例如在容量固定（有界）的队列中 NullPointerException:data==null时抛出
	 * IllegalArgumentException:队满,使用该方法可以使Queue的容量固定
	 * 
	 * @param data
	 * @return
	 */
	@Override
	public boolean offer(T data) {

		if (data == null) {
			throw new NullPointerException("The data can\'t be null");
		}

		if (this.front == (this.rear + 1) % this.elementData.length) {
			throw new IllegalArgumentException("The capacity of SeqQueue has reached its maximum");
		}

		elementData[this.rear] = data;
		this.rear = (this.rear + 1) % elementData.length;
		size++;
		modCount++;
		return true;
	}

	@Override
	public T peek() {
		return elementData[front];
	}

	@Override
	public T element() {

		if (isEmpty()) {
			throw new NoSuchElementException("The SeqQueue is empty");
		}
		return peek();
	}

	/**
	 * 出队,执行删除操作,返回队头元素,若队列为空,返回null
	 */
	@Override
	public T poll() {
		T temp = this.elementData[this.front];
		this.front = (this.front + 1) % this.elementData.length;
		size--;
		modCount++;
		return temp;
	}

	@Override
	public T remove() {
		if (isEmpty()) {
			throw new NoSuchElementException("The SeqQueue is empty");
		}
		return poll();
	}

	@Override
	public void clearQueue() {
		for (int i = this.front; i != this.rear; i = (i + 1) % elementData.length) {
			elementData[i] = null;
		}

		this.front = this.rear = 0;
		size = 0;
		modCount++;
	}

}

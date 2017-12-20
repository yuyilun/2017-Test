package cn.test.pattern;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
/**
 * 泛型:
 * <T> :确定java类型
 * <?> :不确定java类型
 * K,V:JAVA键值对key,value
 * <? extends E> :upper bounds wildcars,上界通配符。表示接收E类型以及E的子类型。
 * <? super E>:lower bounds wildcars,下界通配符。表示接收E类型以及E的父类型。
 * @author xyd-yuyilun
 *
 */
public class TestDifferenceBetweenObjectAndT{
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void ListTest() {
		LinkedList  ll=new LinkedList ();
		ll.add("1");
		ll.add("2");
		ll.add("3");
		ll.add("4");
		List list=new ArrayList(ll);
		for(int i=0;i<list.size();i++) {
			System.out.println(list.get(i));
		}
	}
	
	public Class<?> cl;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList<? extends Apple> 	al1	=	new ArrayList();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList<? super Apple> 	al2	=	new ArrayList();
	
	public class ObjT<T>{
		public List<T> list = new ArrayList<T>();   
	}
	
	public interface Box<T> {
	    public T get();
	    public void put(T element);
	}
	
	public class BoxImpl<T> implements Box<T>{
		private T t;
		public BoxImpl(T t) {
			this.t=t;
		}
		@Override
		public T get() {
			return t;
		}
		@Override
		public void put(T element) {
			this.t=element;
		}
		
		
	}
	
	@Test
	public void UpperAndLower() {
		BoxImpl<? extends Fruit> biE=new BoxImpl<Fruit>(new Fruit());
		BoxImpl<? super Fruit> biS=new BoxImpl<Fruit>(new Fruit());
		
		//biE.put(new Fruit());
		//biE.put(new Apple());
		//Apple f=biE.get();
		//Fruit f=biE.get();
		System.out.println(biE.get());
		
		
		biS.put(new Fruit());
		biS.put(new Apple());
		
		//Apple Apple = (cn.test.pattern.Apple) biS.get();
		System.out.println(biS.get());
	}
	
	
	public class BoxGT{
		public <T> Object get(Box<T> box) {
			return box.get();
		}
		
		public <T> void put(Box<T> box) {
			boxHelper(box);
		}
		
		private<T> void boxHelper(Box<T> box) {
			box.put(box.get());
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void boxTest() {
		Box box=new BoxImpl(new Apple());
		box.put("1234");
		
		BoxGT bg=new BoxGT();
		System.out.println(bg.get(box));
		bg.put(box);
		box.put(12345);
		System.out.println(bg.get(box));
	}
	
	
	@SuppressWarnings("rawtypes")
	@Test
	public void test() {
		ObjT<Map> test = new ObjT<Map>();
		MapImpl mi=new MapImpl();
        test.list.add(mi);
        System.out.println(test.list);
	}
	
	public interface Map<K,V>{
		public V getV();
		public K getK();
	}
	
	@SuppressWarnings({ "unused", "rawtypes" })
	private class MapImpl<K,V> implements Map{
		private K key;
		private V value;
		public MapImpl() {
		}
		
		public MapImpl(K key,V value) {
			this.key=key;
			this.value=value;
		}
		
		public V getV() {
			return value;
		}
		
		public K getK() {
			return key;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void printList1(List<Object> list) {
        for (Object elem : list)
            System.out.println(elem + " ");
        System.out.println();
    }

    public static <T> void printList2(List<T> list) {
        for (T elem : list)
            System.out.println(elem + " ");
        System.out.println();
    }

    public static  void printList3(List<?> list) {
        for (int i = 0;i<list.size();i++)
            System.out.println(list.get(i) + " ");
        System.out.println();
    }

    public static void main(String[] args) {
        List<Integer> test1 = Arrays.asList(1, 2, 3);
        List<String>  test2 = Arrays.asList("one", "two", "three");
        List<Object> test3 = Arrays.asList(2, "two", new Object());
        List<? super Fruit> test4 = Arrays.asList(new Fruit(),new Banana(),new XiGua());
        /*
        * 下面这句会编译报错，因为参数不能转化成功
        * */
       // printList1(test4);
        /**/
        printList1(test3);
        printList1(test3);
        printList2(test1);
        printList2(test2);
        printList2(test3);
        printList2(test4);
        printList3(test1);
        printList3(test2);
        printList3(test3);
        printList3(test4);
    }
}

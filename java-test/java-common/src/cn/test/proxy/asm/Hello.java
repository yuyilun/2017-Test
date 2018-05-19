package cn.test.proxy.asm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 1��ʹ�������У�����asm��ʽ
 * java -classpath "D:\software\libs\org\ow2\asm\asm-util\5.2\asm-util-5.2.jar;D:\software\libs\org\ow2\asm\asm\5.2\asm-5.2.jar" org.objectweb.asm.util.ASMifier target/classes/cn/test/proxy/asm/Hello.class
 * 
 * 2��ʹ��bytecode���ߣ�eclipse�а�װ��
 * 
 * @author xyd-yuyilun
 *
 */
public class Hello {
	 // ���� һ������
    public static final String FLAG = "���ǳ���";

    // ��ͨ����
    public void display(){
        for (int i = 0; i < 8; i++) {
            System.out.println(">>>>>>>>>>" + FLAG);
        }
    }

    // ����List����ֵ
    public List<String> testList(){
        List<String> list = new ArrayList<>();
        list.add("Tome");
        list.add("Jack");
        list.add("Lily");
        System.out.println(">>>>>>>>>>testList > list.size = " + list.size());
        return list;
    }

    // ���ͷ���ֵ������List��Map
    // ��������������ΪMap����
    public List<Map<String, String>> testMapList(boolean val, Map<String, String>... map){
        List<Map<String, String>> list = new ArrayList<>();
        if(val){
            for (Map<String, String> m : map) {
                list.add(m);
            }
        }
        System.out.println(">>>>>>>>>>testMapList > list.size = " + list.size());
        return list;
    }
}

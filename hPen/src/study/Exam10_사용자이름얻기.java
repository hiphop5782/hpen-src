package study;

import java.util.Iterator;
import java.util.Properties;

public class Exam10_사용자이름얻기 {
	public static void main(String[] args) {
		Properties p = System.getProperties();
		Iterator<?> it = p.keySet().iterator();
		
		while(it.hasNext()){
			System.out.println(it.next());
		}
		
		System.out.println("======================");
		System.out.println(System.getProperty("user.name"));
		System.out.println(System.getProperty("user.dir"));
		System.out.println(System.getProperty("user.home"));
	}
}

package com.hacademy.test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import lombok.Getter;

class A {
	@Getter
	private C c;
}
class B extends A{}
class C {}

public class Test15_상속관계인젝션테스트 {
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException {
		B b = B.class.getDeclaredConstructor().newInstance();
		Field f = B.class.getSuperclass().getDeclaredField("c");
		f.setAccessible(true);
		f.set(b, new C());
		System.out.println(b.getC());
	}
}

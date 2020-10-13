package com.hpen.test;

import java.util.LinkedList;

public class Exam32_LinkedList {
	public static void main(String[] args) {
		LinkedList<Integer> list = new LinkedList<>();
		for(int i=1; i <= 10; i++) list.addLast(i);
		
		System.out.println(list.peekLast());
		System.out.println(list.peekLast());
		System.out.println(list.peekLast());
		System.out.println(list.peekLast());
		System.out.println(list.peekLast());
		System.out.println(list.peekLast());
	}
}

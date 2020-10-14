package com.hpen.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Test {
	public static void main(String[] args) {
		int first = 4;
		int five = (first + 4) / 5;
		List<Integer> list = new ArrayList<>();
		for(int i=0; i <= five; i++) {
			int second = first - i * 5;
			int three = (second + 2) / 3;
			int remain = Math.abs(second - three * 3);
			if(remain != 0) continue;
			list.add(i + three);
		}
		if(list.size() > 0)
			System.out.println(Collections.min(list));
		else
			System.out.println(-1);
	}
}

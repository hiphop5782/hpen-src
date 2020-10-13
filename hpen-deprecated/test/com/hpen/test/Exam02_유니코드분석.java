package com.hpen.test;

public class Exam02_유니코드분석 {
	public static void main(String[] args) {
		int s = 'ㄱ';
		int m = 'ㅏ';
		int e = 'ㅇ';
		System.out.println(s +", "+m +", "+e);
		int v = '강';
		System.out.println(v);
		System.out.println(v - 0xAC00);
		v -= 0xAC00;
		int vs = (((v - (v % 28)) / 28) / 21) + '가';
		System.out.println(vs);
		int vm = (((v - (v % 28)) / 28) % 21) + '가';
		System.out.println(vm);
		int ve = (v % 28) + '가' +1;
		System.out.println(ve);
		
		int vv = 0xAC00 + 28 * 21 * (vs - '가') + 28 * (vm - '가') + (ve - '가' -1);
		System.out.println(vv);
		
	}
}





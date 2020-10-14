package com.hpen.test;

import java.awt.Point;

public class Exam01_각도계산 {
	public static void main(String[] args) {
		Point start = new Point(50, 50);
		Point end = new Point(200, 200);
		
		double dx = end.x - start.x;
		double dy = end.y - start.y;
		System.out.println("dx = "+dx +", dy = "+dy);
		
		int angle = (int)(Math.atan(dy/dx) * 180 / Math.PI);
		System.out.println("angle = "+angle);
		
		double range = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
		System.out.println("range = "+range);
		
		int gap = 10;
		int radius = 5;
		
		double p1_x = start.x + Math.cos(angle+gap) * radius;
		double p1_y = start.y + Math.sin(angle+gap) * radius;
		double p2_x = start.x + Math.cos(angle-gap) * radius;
		double p2_y = start.y + Math.sin(angle-gap) * radius;
		System.out.println("p1_x = "+p1_x);
		System.out.println("p1_y = "+p1_y);
		System.out.println("p2_x = "+p2_x);
		System.out.println("p2_y = "+p2_y);
	}
}

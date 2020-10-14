package com.hpen.util;

import java.awt.Point;

public class MathManager {
	public static int getAngle(Point s, Point e){
		return getAngle(s.x, s.y, e.x, e.y);
	}
	public static int getAngle(int sx, int sy, int ex, int ey){
		double dx = ex - sx;
		double dy = ey - sy;
		int angle = (int)(Math.atan2(dy, dx) * 180 / Math.PI);
		return angle > 0 ? angle : angle + 360;
	}
	
	public static Point getPoint(int x, int y, int angle, int radius){
		return new Point(
					(int)(x + Math.cos(angle * Math.PI/180) * radius),
					(int)(y + Math.sin(angle * Math.PI/180) * radius)
				);
	}
	
	public static final int UPPER = 1;
	public static final int LOWER = -1;
	public static Point getCrossPoint(Point s, Point e, int distance, int updown) {
		int angle = getAngle(s, e);
		int cross_angle;
		if(updown == UPPER) {
			cross_angle = (angle + 360 - 90) % 360; 
		}else {
			cross_angle = (angle + 90) % 360;
		}
		int mx = (s.x + e.x) / 2, my = (s.y + e.y) / 2;
		Point p = getPoint(mx, my, cross_angle, distance);
		return p;
	}
	public static int getDistance(Point s, Point e) {
		//return 루트(밑변² + 높이²);
		double w = Math.abs(s.x - e.x);
		double h = Math.abs(s.y - e.y);
		return (int)Math.sqrt(Math.pow(w, 2) + Math.pow(h, 2));
	}
	
	/**
	 * 직선과 원의 충돌 검사 메소드
	 * @param p1
	 * @param p2
	 * @param test
	 * @param r
	 * @return
	 */
	public static boolean checkCrash(Point p1, Point p2, Point test, int r) {
		if (r < 0) {
			return false;
		} else if (p1.x != p2.x) {
			if (p1.y == p2.y) {
				if (Math.sqrt((test.x - p1.x) * (test.x - p1.x) + (test.y - p1.y) * (test.y - p1.y)) <= r) {
					return true;
				} else {
					return false;
				}
			} else {
				double c = (double)(p1.y - p2.y) / (p1.x - p2.x);
				double d = p1.y - c * p1.x;
				double e = d - test.y;
				double D = (c * e - test.x) * (c * e - test.x) - (test.x * test.x + e * e - r * r) * (c * c + 1);
//				System.out.println("D = "+D);
				if (D >= 0) {
					if ((test.x - c * e + Math.sqrt(D)) / (c * c + 1) >= Math.min(p1.x, p2.x)
							&& (test.x - c * e - Math.sqrt(D)) / (c * c + 1) <= Math.max(p1.x, p2.x)) {
						// 닿인거다
						return true;
					} else {
						// 안닿인거다
						return false;
					}
				} else {
					// 안닿인거다
					return false;
				}
			}
		} else {
			double D = test.y * test.y - (p1.x - test.x) * (p1.x - test.x) - test.y * test.y + r * r;
//			System.out.println("D = "+D);
			if (D >= 0) {
				if (test.y + Math.sqrt(D) > Math.min(p1.y, p2.y) && test.y - Math.sqrt(D) < Math.max(p1.y, p2.y)) {
					// 닿인거
					return true;
				} else {
					// 안닿인거
					return false;
				}
			} else {
				// 안닿인거
				return false;
			}
		}
	}
}

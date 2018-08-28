package com.hpen.test;

import java.awt.Rectangle;

import com.hpen.update.subutil.ScreenManager;

public class Exam30_ScreenManager {
	public static void main(String[] args) {
		ScreenManager manager = ScreenManager.getManager();
		System.out.println(manager.getCurrentMonitorRect());
		
		for(Rectangle rect : manager.getAllMonitorsize()) {
			System.out.println(rect);
		}
	}
}

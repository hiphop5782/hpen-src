package com.hpen.util;

import com.hakademy.utility.magnify.MagnificationManager;

public class MagnifyManager {
	private static boolean isMagnify = false;
	public static void work() {
		if(!isMagnify) {
			MagnificationManager.getManager().start();
			System.out.println("start = "+Thread.activeCount());
			stateChangeAfterDelay(10, true);
		}
		else {
			MagnificationManager.getManager().stop();
			System.out.println("stop = "+Thread.activeCount());
			stateChangeAfterDelay(1000, false);
		}
	}
	
	public static void stateChangeAfterDelay(int time, boolean state) {
		Thread t = new Thread(()->{
			try {Thread.sleep(time);}catch(Exception e) {}
			isMagnify = state;
		});
		t.start();
	}
}

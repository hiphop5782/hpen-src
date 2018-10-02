package com.hpen.util;

import com.hakademy.utility.magnify.MagnificationManager;

public class MagnifyManager {
	private static boolean isMagnify = false;
	public static void work() {
		if(!isMagnify) {
			MagnificationManager.getManager().start();
//			System.out.println("start = "+Thread.activeCount());
			isMagnify = true;
		}
		else {
			MagnificationManager.getManager().stop();
//			System.out.println("stop = "+Thread.activeCount());
			isMagnify = false;
		}
	}
}

package com.hpen.util;

import com.hakademy.utility.magnify.MagnificationManager;

public class MagnifyManager {
	private static boolean isMagnify = false;
	public static void work() {
		if(!isMagnify) {
			MagnificationManager.getManager().start();
			isMagnify = true;
		}
		else {
			MagnificationManager.getManager().stop();
			isMagnify = false;
		}
	}
}

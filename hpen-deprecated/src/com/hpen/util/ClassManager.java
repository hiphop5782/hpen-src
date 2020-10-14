package com.hpen.util;

public class ClassManager {
	public static void initialize() {
		try {
			Class.forName("com.hakademy.utility.hook.KeyboardHook");
			Class.forName("com.hakademy.utility.hook.MouseHook");
			Class.forName("com.hpen.util.key.GlobalkeyManager");
			Class.forName("com.hpen.property.PropertyLoader");
		}catch(Exception e) {
			System.err.println("class not found");
			System.err.println(e.getMessage());
		}
	}
}

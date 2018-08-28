package com.hpen.util;

public class ClassManager {
	public static void initialize() {
		try {
			Class.forName("com.hpen.util.key.GlobalkeyManager");
//			Class.forName("com.hpen.util.file.VersionManager");
//			Class.forName("com.hpen.value.Version");
//			Class.forName("com.hpen.property.PropertyLoader");
		}catch(Exception e) {
			System.err.println("class not found");
			System.err.println(e.getMessage());
		}
	}
}

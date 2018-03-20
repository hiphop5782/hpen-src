package com.hpen.util;

public class ClassManager {
	public static void initialize() {
		try {
			Class.forName("com.hpen.property.PropertyLoader");
//			Class.forName("com.hpen.value.Version");
			Class.forName("com.hpen.util.TrayManager");
			Class.forName("com.hpen.util.key.GlobalkeyManager");
//			Class.forName("com.hpen.property.DrawingOption");
			Class.forName("com.hpen.draw.ui.DrawingFrame");
//			Class.forName("com.hpen.property.CaptureOption");
			Class.forName("com.hpen.draw.ui.CaptureFrame");
		}catch(Exception e) {
			System.err.println("class not found");
			System.err.println(e.getMessage());
		}
	}
}

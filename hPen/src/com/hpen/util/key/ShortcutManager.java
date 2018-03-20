package com.hpen.util.key;

import java.io.IOException;

import com.hpen.draw.ui.CaptureFrame;
import com.hpen.draw.ui.DrawingFrame;
import com.hpen.livezoom.ui.ZoomFrame;

/**
 * 단축키를 관리하는 클래스
 * @author Hwang
 *
 */
public class ShortcutManager {
	private static int capture = 1;
	private static int note = 2;
	private static int zoom = 3;
	private static int magnify = 4;
	
	public static int getCapture_int() {
		return capture;
	}
	public static void setCapture_int(int capture) {
		ShortcutManager.capture = capture;
	}
	public static int getZoom_int() {
		return zoom;
	}
	public static void setZoom_int(int zoom) {
		ShortcutManager.zoom = zoom;
	}
	public static int getNote_int() {
		return note;
	}
	public static void setNote_int(int note) {
		ShortcutManager.note = note;
	}
	public static int getMagnify_int() {
		return magnify;
	}
	public static void setMagnify_int(int magnify) {
		ShortcutManager.magnify = magnify;
	}
	public static char getCapture_char() {
		return (char) (capture+48);
	}
	public static char getNote_char() {
		return (char) (note+48);
	}
	public static char getZoom_char(){
		return (char) (zoom+48);
	}
	public static char getMagnify_char() {
		return (char) (magnify+48);
	}
	
	private static Process magnify_proc;
	public static void execute(int key) {
		if (key == getNote_int()) {
			DrawingFrame.start();
		} else if (key == getCapture_int()) {
			CaptureFrame.start();
		} else if (key == getZoom_int()) {
			ZoomFrame.start();
		} else if (key == getMagnify_int()) {
			if(magnify_proc == null) {
				String command = System.getenv("windir")+"\\system32\\magnify.exe";
				ProcessBuilder builder = new ProcessBuilder(new String[] {"cmd.exe", "/C", command});
				try {
					magnify_proc = builder.start();
				}catch(IOException e) {
					magnify_proc = null;
				}
			}else {
				ProcessBuilder builder = new ProcessBuilder(new String[] {"tskill", "/a", "magnify"});
				try {
					builder.start();
				}catch(Exception e) {}
				magnify_proc = null;
			}
		}
	}
}

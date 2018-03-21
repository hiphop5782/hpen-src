package com.hpen.util.key;

import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.KeyStroke;

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
	
	/**
	 * String 형식의 키값을 KeyStroke로 변환하는 메소드<br>
	 * 일반키는 A, S 등의 형식을 띄며<br>
	 * 조합키는 CTRL+S, CTRL+ALT+U 등의 형태를 띔<br>
	 * +로 분리하여 사용<br>
	 * @param key
	 * @return
	 */
	public static KeyStroke convert(String key) {
		String[] sep = key.toUpperCase().split("+");
		//일반 키
		if(sep.length == 1) {
			return KeyStroke.getKeyStroke(0, sep[0].trim().charAt(0));
		}
		//조합키
		else{
			char press = sep[sep.length-1].trim().charAt(0);
			int mask = 0;
			for(int i=0; i < sep.length-1; i++) {
				switch(sep[i].trim()) {
				case "CTRL":	mask |= KeyEvent.CTRL_DOWN_MASK; break;
				case "ALT":		mask |= KeyEvent.ALT_DOWN_MASK; break;
				case "SHIFT":	mask |= KeyEvent.SHIFT_DOWN_MASK; break;
				}
			}
			return KeyStroke.getKeyStroke(mask, press);
		}
	}
	
	/**
	 * KeyStroke를 String으로 변환<br>
	 * A, 1, F10, ALT+5 등으로 변환되어 나감<br>
	 * 조합키는 + 형태로 연결됨, 모두 대문자이며 띄어쓰기 없음<br>
	 * @param stroke
	 * @return
	 */
	public static String convert(KeyStroke stroke) {
		StringBuffer buffer = new StringBuffer();
		//modifier check
		int modifier = stroke.getModifiers();
		if((modifier & (KeyEvent.SHIFT_DOWN_MASK | KeyEvent.SHIFT_MASK)) != 0) 
			buffer.append("SHIFT+");
		if((modifier & (KeyEvent.CTRL_DOWN_MASK | KeyEvent.CTRL_MASK)) != 0) 
			buffer.append("CTRL+");
		if((modifier & (KeyEvent.ALT_DOWN_MASK | KeyEvent.ALT_MASK)) != 0) 
			buffer.append("ALT+");
		//key check
		int keyCode = stroke.getKeyCode();
		switch(keyCode) {
		//number
		case KeyEvent.VK_0:		case KeyEvent.VK_1:		case KeyEvent.VK_2:
		case KeyEvent.VK_3:		case KeyEvent.VK_4:		case KeyEvent.VK_5:
		case KeyEvent.VK_6:		case KeyEvent.VK_7:		case KeyEvent.VK_8:
		case KeyEvent.VK_9:
		//alphabet
		case KeyEvent.VK_A:		case KeyEvent.VK_B:		case KeyEvent.VK_C:
		case KeyEvent.VK_D:		case KeyEvent.VK_E:		case KeyEvent.VK_F:
		case KeyEvent.VK_G:		case KeyEvent.VK_H:		case KeyEvent.VK_I:
		case KeyEvent.VK_J:		case KeyEvent.VK_K:		case KeyEvent.VK_L:
		case KeyEvent.VK_M:		case KeyEvent.VK_N:		case KeyEvent.VK_O:
		case KeyEvent.VK_P:		case KeyEvent.VK_Q:		case KeyEvent.VK_R:
		case KeyEvent.VK_S:		case KeyEvent.VK_T:		case KeyEvent.VK_U:
		case KeyEvent.VK_V:		case KeyEvent.VK_W:		case KeyEvent.VK_X:
		case KeyEvent.VK_Y:		case KeyEvent.VK_Z:
			buffer.append((char)keyCode);			break;
		
		//function key
		case KeyEvent.VK_F1:			buffer.append("F1");	break;
		case KeyEvent.VK_F2:			buffer.append("F2");	break;
		case KeyEvent.VK_F3:			buffer.append("F3");	break;
		case KeyEvent.VK_F4:			buffer.append("F4");	break;
		case KeyEvent.VK_F5:			buffer.append("F5");	break;
		case KeyEvent.VK_F6:			buffer.append("F6");	break;
		case KeyEvent.VK_F7:			buffer.append("F7");	break;
		case KeyEvent.VK_F8:			buffer.append("F8");	break;
		case KeyEvent.VK_F9:			buffer.append("F9");	break;
		case KeyEvent.VK_F10:		buffer.append("F10");	break;
		case KeyEvent.VK_F11:		buffer.append("F11");	break;
		case KeyEvent.VK_F12:		buffer.append("F12");	break;
		
		//
		}
		return null;
	}
}







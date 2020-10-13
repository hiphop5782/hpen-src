package com.hpen.util.key;

import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

public class KeyManager {
	//KeyStroke manager
	
	//special key
	public static final KeyStroke esc = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
	public static final KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
	public static final KeyStroke backspace = KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0);
	public static final KeyStroke ctrl = KeyStroke.getKeyStroke(KeyEvent.VK_CONTROL, 0);
	public static final KeyStroke tab = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0);
	public static final KeyStroke korean = KeyStroke.getKeyStroke(KeyEvent.VK_KANA, 0);
	
	//function key
	public static final KeyStroke f1 = KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0);
	public static final KeyStroke f2 = KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0);
	public static final KeyStroke f3 = KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0);
	public static final KeyStroke f4 = KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0);
	public static final KeyStroke f5 = KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0);
	public static final KeyStroke f6 = KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0);
	public static final KeyStroke f7 = KeyStroke.getKeyStroke(KeyEvent.VK_F7, 0);
	public static final KeyStroke f8= KeyStroke.getKeyStroke(KeyEvent.VK_F8, 0);
	public static final KeyStroke f9 = KeyStroke.getKeyStroke(KeyEvent.VK_F9, 0);
	public static final KeyStroke f10 = KeyStroke.getKeyStroke(KeyEvent.VK_F10, 0);
	public static final KeyStroke f11 = KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0);
	public static final KeyStroke f12 = KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0);
	
	//alphabet
	public static final KeyStroke q = KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0);
	public static final KeyStroke w = KeyStroke.getKeyStroke(KeyEvent.VK_W, 0);
	public static final KeyStroke e = KeyStroke.getKeyStroke(KeyEvent.VK_E, 0);
	public static final KeyStroke r = KeyStroke.getKeyStroke(KeyEvent.VK_R, 0);
	public static final KeyStroke t = KeyStroke.getKeyStroke(KeyEvent.VK_T, 0);
	public static final KeyStroke a = KeyStroke.getKeyStroke(KeyEvent.VK_A, 0);
	public static final KeyStroke s = KeyStroke.getKeyStroke(KeyEvent.VK_S, 0);
	public static final KeyStroke d = KeyStroke.getKeyStroke(KeyEvent.VK_D, 0);
	public static final KeyStroke f = KeyStroke.getKeyStroke(KeyEvent.VK_F, 0);
	public static final KeyStroke z = KeyStroke.getKeyStroke(KeyEvent.VK_Z, 0);
	public static final KeyStroke x = KeyStroke.getKeyStroke(KeyEvent.VK_X, 0);
	public static final KeyStroke c = KeyStroke.getKeyStroke(KeyEvent.VK_C, 0);
	public static final KeyStroke v = KeyStroke.getKeyStroke(KeyEvent.VK_V, 0);
	
	//number key
	public static final KeyStroke num1 = KeyStroke.getKeyStroke(KeyEvent.VK_1, 0);
	public static final KeyStroke num2 = KeyStroke.getKeyStroke(KeyEvent.VK_2, 0);
	public static final KeyStroke num3 = KeyStroke.getKeyStroke(KeyEvent.VK_3, 0);
	public static final KeyStroke num4 = KeyStroke.getKeyStroke(KeyEvent.VK_4, 0);
	public static final KeyStroke num5 = KeyStroke.getKeyStroke(KeyEvent.VK_5, 0);
	public static final KeyStroke num6 = KeyStroke.getKeyStroke(KeyEvent.VK_6, 0);
	public static final KeyStroke num7 = KeyStroke.getKeyStroke(KeyEvent.VK_7, 0);
	public static final KeyStroke num8 = KeyStroke.getKeyStroke(KeyEvent.VK_8, 0);
	public static final KeyStroke num9 = KeyStroke.getKeyStroke(KeyEvent.VK_9, 0);
	public static final KeyStroke num0 = KeyStroke.getKeyStroke(KeyEvent.VK_0, 0);
	
	//ctrl+key
	public static final KeyStroke ctrl_z = KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK);
	public static final KeyStroke ctrl_s = KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK);
	public static final KeyStroke ctrl_y = KeyStroke.getKeyStroke(KeyEvent.VK_Y, KeyEvent.CTRL_DOWN_MASK);
	public static final KeyStroke ctrl_w = KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.CTRL_DOWN_MASK);
	public static final KeyStroke ctrl_e = KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK);
	public static final KeyStroke ctrl_r = KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK);
	public static final KeyStroke shift_space = KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, KeyEvent.SHIFT_DOWN_MASK);
	
	//alt+key
	public static final KeyStroke alt1 = KeyStroke.getKeyStroke(KeyEvent.VK_1, KeyEvent.ALT_DOWN_MASK);
	public static final KeyStroke alt2 = KeyStroke.getKeyStroke(KeyEvent.VK_2, KeyEvent.ALT_DOWN_MASK);
	public static final KeyStroke alt3 = KeyStroke.getKeyStroke(KeyEvent.VK_3, KeyEvent.ALT_DOWN_MASK);
	public static final KeyStroke alt4 = KeyStroke.getKeyStroke(KeyEvent.VK_4, KeyEvent.ALT_DOWN_MASK);
	public static final KeyStroke alt5 = KeyStroke.getKeyStroke(KeyEvent.VK_5, KeyEvent.ALT_DOWN_MASK);
	public static final KeyStroke alt6 = KeyStroke.getKeyStroke(KeyEvent.VK_6, KeyEvent.ALT_DOWN_MASK);
	
	//number pad
	public static final KeyStroke numpad1 = KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD1, 0);
	public static final KeyStroke numpad2 = KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD2, 0);
	public static final KeyStroke numpad3 = KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD3, 0);
	public static final KeyStroke numpad4 = KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD4, 0);
	public static final KeyStroke numpad5 = KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD5, 0);
	public static final KeyStroke numpad6 = KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD6, 0);
	public static final KeyStroke numpad7 = KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD7, 0);
	public static final KeyStroke numpad8 = KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD8, 0);
	public static final KeyStroke numpad9 = KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD9, 0);
	public static final KeyStroke numpad0 = KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD0, 0);
	
	public static final KeyStroke altnumpad1 = KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD1, KeyEvent.ALT_DOWN_MASK);
	public static final KeyStroke altnumpad2 = KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD2, KeyEvent.ALT_DOWN_MASK);
	public static final KeyStroke altnumpad3 = KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD3, KeyEvent.ALT_DOWN_MASK);
	public static final KeyStroke altnumpad4 = KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD4, KeyEvent.ALT_DOWN_MASK);
	public static final KeyStroke altnumpad5 = KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD5, KeyEvent.ALT_DOWN_MASK);
	public static final KeyStroke altnumpad6 = KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD6, KeyEvent.ALT_DOWN_MASK);
	public static final KeyStroke altnumpad7 = KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD7, KeyEvent.ALT_DOWN_MASK);
	public static final KeyStroke altnumpad8 = KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD8, KeyEvent.ALT_DOWN_MASK);
	public static final KeyStroke altnumpad9 = KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD9, KeyEvent.ALT_DOWN_MASK);
	public static final KeyStroke altnumpad0 = KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD0, KeyEvent.ALT_DOWN_MASK);
}












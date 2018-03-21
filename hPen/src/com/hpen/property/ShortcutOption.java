package com.hpen.property;

import java.util.Properties;

public class ShortcutOption {
	//property names
	public static final String capture = "hpen.shortcut.capture";
	public static final String draw = "hpen.shortcut.draw";
	public static final String livezoom = "hpen.shortcut.livezoom";
	public static final String magnify = "hpen.shortcut.magnify";
	
	public static final String color1 = "hpen.shortcut.draw.color1";
	public static final String color2 = "hpen.shortcut.draw.color2";
	public static final String color3 = "hpen.shortcut.draw.color3";
	public static final String color4 = "hpen.shortcut.draw.color4";
	public static final String color5 = "hpen.shortcut.draw.color5";
	public static final String color6 = "hpen.shortcut.draw.color6";
	public static final String color7 = "hpen.shortcut.draw.color7";
	public static final String color8 = "hpen.shortcut.draw.color8";
	public static final String color9 = "hpen.shortcut.draw.color9";
	public static final String color0 = "hpen.shortcut.draw.color0";
	
	public static final String circle = "hpen.shortcut.draw.circle";
	public static final String square = "hpen.shortcut.draw.square";
	public static final String line = "hpen.shortcut.draw.line";
	public static final String curve = "hpen.shortcut.draw.line.curve";
	public static final String reverseArrow = "hpen.shortcut.draw.arrow.reverse";
	public static final String correctArrow = "hpen.shortcut.draw.arrow.correct";
	public static final String bothArrow = "hpen.shortcut.draw.arrow.both";
	public static final String thunder = "hpen.shortcut.draw.thunder";
	
	public static final String clear = "hpen.shortcut.draw.clear";
	public static final String restore = "hpen.shortcut.draw.restore";
	public static final String save = "hpen.shortcut.draw.save";
	public static final String text = "hpen.shortcut.draw.text";
	public static final String undo = "hpen.shortcut.draw.undo";
	public static final String redo = "hpen.shortcut.draw.redo";
	
	//singleton
	private static ShortcutOption instance = new ShortcutOption();
	public static ShortcutOption getInstance() {
		return instance;
	}
	private ShortcutOption() {}
	
	//properties            
	private Properties options = new Properties();
	public void setOptions(Properties options) {
		this.options = options;
	}
	public Properties getOptions() {
		return options;
	}
	
	//setter and getter
//	public void setCaptureKey()
	
}

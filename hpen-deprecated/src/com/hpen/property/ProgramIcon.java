package com.hpen.property;

import java.awt.Image;
import java.awt.Toolkit;

import com.hpen.Starter;

public class ProgramIcon {
	private static Image icon;
	public static Image getIcon(){
		return icon;
	}
	static{
		icon = Toolkit.getDefaultToolkit().createImage(Starter.class.getResource("resource/icon.png"));
	}
}

package com.hpen.test;

import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;

import com.hpen.draw.shapes.Icon;
import com.hpen.util.image.IconManager;

public class Exam17_changeEmoticonCursor {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setUndecorated(true);
		frame.setVisible(true);
		
		Icon icon = IconManager.showIconDialog(frame);
		System.out.println(icon.getImageIcon());
		if(icon.getImageIcon() == null) return;
		frame.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(icon.getImageIcon().getImage(), new Point(0,0), "iconCursor"));
	}
}

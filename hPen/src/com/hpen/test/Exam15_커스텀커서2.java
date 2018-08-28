package com.hpen.test;

import java.awt.Color;
import java.awt.Cursor;
import java.io.IOException;

import javax.swing.JFrame;

import com.hpen.util.CursorManager;

public class Exam15_Ä¿½ºÅÒÄ¿¼­2 {
	public static void main(String[] args) throws Exception{
		JFrame frame = new JFrame();
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		Cursor cursor = CursorManager.createCircleCursor(Color.red, 3);
		frame.setCursor(cursor);
	}
}

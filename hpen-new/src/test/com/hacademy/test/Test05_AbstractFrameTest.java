package com.hacademy.test;

import javax.swing.JFrame;

import com.hacademy.hpen.ui.MouseGuideFrame;
import com.hacademy.hpen.ui.MultiOptionFrame;

public class Test05_AbstractFrameTest {
	public static void main(String[] args) throws InterruptedException {
		MouseGuideFrame f = new MouseGuideFrame();
		f.setBounds(100, 100, 500, 500);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
//		System.out.println(f.is(MultiOptionFrame.FULLSCREEN_MODE));
//		System.out.println(f.is(MultiOptionFrame.SELECTION_MODE));
//		System.out.println(f.is(MultiOptionFrame.PAUSE_MODE));
//		System.out.println(f.is(MultiOptionFrame.TRANSPARENT_MODE));
//		System.out.println(f.is(MultiOptionFrame.TRANSPARENT_MODE | MultiOptionFrame.FULLSCREEN_MODE));
		while(true) {
			f.repaint();
			Thread.sleep(1000/30);
		}
	}
}

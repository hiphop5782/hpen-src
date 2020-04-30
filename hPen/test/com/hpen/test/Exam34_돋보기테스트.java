package com.hpen.test;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class Exam34_돋보기테스트 {
	public static void main(String[] args) throws AWTException, InterruptedException {
		Robot r = new Robot();
		int count = 0;
		while(true) {
			r.keyPress(KeyEvent.VK_ALT);
			r.keyPress(KeyEvent.VK_4);
			Thread.sleep(30);
			r.keyRelease(KeyEvent.VK_4);
			r.keyRelease(KeyEvent.VK_ALT);
			Thread.sleep(1000);
			System.out.println("count = "+(++count));
		}
	}
}
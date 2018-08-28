package com.hpen.test;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;

import javax.swing.SwingUtilities;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyAdapter;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseMotionListener;

public class Exam26_마우스가두기 {
	
	static class MouseLocker implements NativeMouseMotionListener{
		Robot r;
		MouseLocker(){
			try {
				r = new Robot();
			}catch(AWTException e) {}
		}
		int x, y;
		Rectangle rect = new Rectangle(100, 100, 400, 400);
		@Override
		public void nativeMouseDragged(NativeMouseEvent e) {
			lock(e.getX(), e.getY());
		}
		@Override
		public void nativeMouseMoved(NativeMouseEvent e) {
			lock(e.getX(), e.getY());
		}
		public void lock(int x, int y) {
			if(x < rect.x) x = rect.x;
			if(x > rect.x+rect.width) x = rect.x+rect.width;
			if(y < rect.y) y = rect.y;
			if(y > rect.y+rect.height) y = rect.y+rect.height;
			r.mouseMove(x, y);
		}
	};
	
	public static void main(String[] args) throws Exception{
		//jnativehook-2.1.0을 이용한 마우스와 키보드 후킹 테스트
		
		NativeKeyListener kListener = new NativeKeyAdapter() {
			@Override
			public void nativeKeyTyped(NativeKeyEvent e) {
				System.out.println(e.getKeyCode() + "("+e.getKeyChar()+") typed");
			}
			
			@Override
			public void nativeKeyReleased(NativeKeyEvent e) {
				System.out.println(e.getKeyCode() + "("+e.getKeyChar()+") released");
			}
			
			@Override
			public void nativeKeyPressed(NativeKeyEvent e) {
				System.out.println(e.getKeyCode() + "("+e.getKeyChar()+") pressed");
			}
		};
		
		try {
			GlobalScreen.registerNativeHook();
//			GlobalScreen.unregisterNativeHook();
//			GlobalScreen.addNativeKeyListener(kListener);
			GlobalScreen.addNativeMouseMotionListener(new MouseLocker());
		}catch(NativeHookException e) {
			e.printStackTrace();
		}
		
	}
}

package com.hpen.draw.ui;

import java.awt.Graphics;
import java.awt.Image;

import com.hakademy.utility.hook.KeyboardHook;
import com.hakademy.utility.screen.ScreenManager;

public abstract class CaptureScreenFrame extends FullScreenFrame{
	protected KeyboardHook hook = KeyboardHook.getInstance();
	
	protected abstract void setKeyboardPrevent() ;
	protected abstract void setKeyboardUnprevent();
	
	/**
	 * 현재 커서가 위치한 화면을 프레임에 보여주는 메소드
	 */
	protected Image background;
	protected void displayCaptureScreen() {
		ScreenManager manager = ScreenManager.getManager();
		background = manager.getCurrentMonitorImage();
		repaint();
	}
	
	@Override
	public void update(Graphics g) {
		paint(g);
	}
	
	@Override
	public void paint(Graphics g) {
		if(background == null) 
			g.fillRect(0, 0, getWidth(), getHeight());
		else
			g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
	}
	
}




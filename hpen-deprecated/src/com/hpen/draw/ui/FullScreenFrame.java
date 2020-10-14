package com.hpen.draw.ui;

import javax.swing.JFrame;

/**
 * 드래그 되지 않는 전체 화면 창을 구현하는 클래스
 * 창의 위치는 마우스 커서가 위치한 곳으로 설정
 * @author hwang
 */
public abstract class FullScreenFrame extends JFrame{
	
	protected int mode;
	protected void setMode(int mode) {
		this.mode = mode;
	}
	public int getMode() {
		return mode;
	}
	
	public FullScreenFrame() {
	}
	
	protected void screen() {
		setUndecorated(true);
		setAlwaysOnTop(true);
		setResizable(false);
		setExtendedState(MAXIMIZED_BOTH);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setFocusTraversalKeysEnabled(false);
	}
	
}

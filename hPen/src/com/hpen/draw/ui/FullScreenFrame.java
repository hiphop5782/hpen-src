package com.hpen.draw.ui;

import java.awt.Rectangle;

import javax.swing.JFrame;

import com.hakademy.utility.screen.ScreenManager;
import com.hpen.util.CursorManager;

/**
 * 드래그 되지 않는 전체 화면 창을 구현하는 클래스
 * 창의 위치는 마우스 커서가 위치한 곳으로 설정
 * @author hwang
 */
public class FullScreenFrame extends JFrame{
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

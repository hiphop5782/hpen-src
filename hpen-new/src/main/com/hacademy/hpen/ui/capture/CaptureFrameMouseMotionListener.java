package com.hacademy.hpen.ui.capture;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import com.hacademy.hpen.util.loader.annotation.Autowired;
import com.hacademy.hpen.util.loader.annotation.Component;

import lombok.Getter;
import lombok.Setter;

@Component
public class CaptureFrameMouseMotionListener extends MouseMotionAdapter{
	
	@Autowired
	private CaptureFrame frame;
	
	@Setter @Getter
	private int x, y;
	
	@Override
	public void mouseMoved(MouseEvent e) {
		this.x = e.getX();
		this.y = e.getY();
	}
}

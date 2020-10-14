package com.hacademy.hpen.ui.capture;

import com.hacademy.hpen.util.loader.annotation.Autowired;
import com.hacademy.hpen.util.loader.annotation.Component;

import lombok.Getter;
import lombok.Setter;

@Component
public class CaptureFramePainter extends Thread{
	
	@Autowired
	private CaptureFrame frame;
	
	@Setter @Getter
	private int fps = 24;
	
	public CaptureFramePainter() {
		setDaemon(true);
	}
	
	private boolean flag = true;
	public void kill() {
		this.flag = false;
	}
	
	@Override
	public synchronized void start() {
		this.flag = true;
		super.start();
	}
	
	@Override
	public void run() {
		while(flag) {
			frame.repaint();
			
			//delay
			try { Thread.sleep(1000L / fps); } catch(Exception e) {}
		}
	}
}

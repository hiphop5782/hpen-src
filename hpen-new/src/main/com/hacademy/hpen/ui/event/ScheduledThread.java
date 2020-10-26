package com.hacademy.hpen.ui.event;

import com.hacademy.hpen.util.loader.annotation.Component;

import lombok.Getter;
import lombok.Setter;

@Component
public class ScheduledThread extends Thread{
	
	@Setter @Getter
	private int fps = 24;
	
	@Setter
	private ScheduledListener listener;
	
	public ScheduledThread() {
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
			if(listener != null) {
				listener.process();
			}
			
			//delay
			try { Thread.sleep(1000L / fps); } catch(Exception e) {}
		}
	}
}

package com.hacademy.hpen.ui.note;

import java.awt.Graphics2D;

import com.hacademy.hpen.ui.MultiOptionFrame;
import com.hacademy.hpen.ui.event.MouseStatus;
import com.hacademy.hpen.ui.event.ScheduledThread;
import com.hacademy.hpen.util.cursor.CursorManager;
import com.hacademy.hpen.util.delay.DelayManager;
import com.hacademy.hpen.util.loader.annotation.Autowired;
import com.hacademy.hpen.util.loader.annotation.Component;
import com.hacademy.hpen.util.screen.ScreenManager;

@Component
public class NoteFullScreeFrame extends MultiOptionFrame{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ScreenManager screenManager;
	
	@Autowired
	private DelayManager delayManager;
	
	@Autowired
	private ScheduledThread painter;
	
	@Autowired
	private MouseStatus mouseStatus;
	
	public void init() {
		delayManager.setTimeout(50L, ()->{
			super.prepare(NOTE_TRANSPARENT_MODE);//option
		});
	}
	
	public void open() {
		setFrameMode(NOTE_TRANSPARENT_MODE);//option
		setScreenRect(screenManager.getCurrentMonitorRect());
		setCursor(CursorManager.createCircleCursor(5));//option
		painter.setFps(24);//option
		painter.setListener(()->{
			repaint();
		});
		painter.start();
		setVisible(true);
	}
	
	@Override
	public void advancedPaint(Graphics2D g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitProcess() {
		// TODO Auto-generated method stub
		setVisible(false);
	}

}

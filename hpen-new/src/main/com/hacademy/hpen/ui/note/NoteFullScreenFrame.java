package com.hacademy.hpen.ui.note;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import com.hacademy.hpen.ui.MultiOptionFrame;
import com.hacademy.hpen.ui.event.MouseEventListener;
import com.hacademy.hpen.ui.event.MouseStatus;
import com.hacademy.hpen.ui.event.ScheduledThread;
import com.hacademy.hpen.ui.option.process.NoteConfiguration;
import com.hacademy.hpen.util.cursor.CursorManager;
import com.hacademy.hpen.util.delay.DelayManager;
import com.hacademy.hpen.util.loader.annotation.Autowired;
import com.hacademy.hpen.util.loader.annotation.Component;
import com.hacademy.hpen.util.screen.ScreenManager;

import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import lc.kra.system.keyboard.event.GlobalKeyListener;

@Component
public class NoteFullScreenFrame extends MultiOptionFrame{
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ScreenManager screenManager;
	
	@Autowired
	private DelayManager delayManager;
	
	@Autowired
	private ScheduledThread painter;
	
	@Autowired
	private MouseStatus mouseStatus;
	
	@Autowired
	private NoteConfiguration conf;
	
	private MouseEventListener mouseListener = new MouseEventListener() {
		public void whenMouseMove(MouseEvent e) {
			
		};
	};

	public void init() {
		mouseStatus.setListener(mouseListener);
		addMouseMotionListener(mouseStatus);
		addMouseWheelListener(mouseStatus);
		addMouseListener(mouseStatus);
		delayManager.setTimeout(50L, ()->{
			super.prepare(conf.isPause() ? NOTE_PAUSE_MODE : NOTE_TRANSPARENT_MODE);//option
		});
	}
	
	public void open() {
		setFrameMode(conf.isPause() ? NOTE_PAUSE_MODE : NOTE_TRANSPARENT_MODE);//option
		setScreenRect(screenManager.getCurrentMonitorRect());
		setCursor(CursorManager.createCircleCursor(conf.getPointerThickness()));//option
		painter.setFps(24);//option
		painter.setListener(()->{
			repaint();
		});
		painter.start();
		setVisible(true);
	}
	
	@Override
	public void advancedPaint(Graphics2D g) {
		
	}

	@Override
	public void exitProcess() {
		setVisible(false);
	}

	@Override
	public void setKeyHook(GlobalKeyboardHook keyHook) {
		keyHook.addKeyListener(new GlobalKeyListener() {
			@Override
			public void keyReleased(GlobalKeyEvent event) {
				
			}
			@Override
			public void keyPressed(GlobalKeyEvent event) {
				
			}
		});
	}

}

package com.hacademy.hpen.ui.note;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.hacademy.hpen.ui.MultiOptionFrame;
import com.hacademy.hpen.ui.event.MouseEventListener;
import com.hacademy.hpen.ui.event.MouseStatus;
import com.hacademy.hpen.ui.event.ScheduledThread;
import com.hacademy.hpen.ui.note.element.Node;
import com.hacademy.hpen.ui.note.element.Rect;
import com.hacademy.hpen.ui.option.process.NoteConfiguration;
import com.hacademy.hpen.util.cursor.CursorManager;
import com.hacademy.hpen.util.delay.DelayManager;
import com.hacademy.hpen.util.loader.annotation.Autowired;
import com.hacademy.hpen.util.loader.annotation.Component;
import com.hacademy.hpen.util.screen.ScreenManager;

import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import lc.kra.system.keyboard.event.GlobalKeyListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Component
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
	
	private List<Node> nodes = new CopyOnWriteArrayList<>();
	/**
	 * Drawing Status
	 * 0 : free drawing
	 * 1 : rect
	 * 2 : oval
	 * 3 : line
	 */
	private int drawStatus;
	public static final int DRAW_FREE = 0;
	public static final int DRAW_RECT = 1;
	public static final int DRAW_OVAL = 2;
	public static final int DRAW_LINE = 3;
	public static final int DRAW_TEXT = 4;
	private Node flashNode;
	
	@Autowired
	private NoteConfiguration conf;
	
	private MouseEventListener mouseListener = new MouseEventListener() {
		public void whenMouseMove(MouseEvent e) {
			System.out.println("move - " + mouseStatus);
		};
		public void whenMouseRelease(MouseEvent e) {
			System.out.println("release - "+mouseStatus);
			Rect rect = new Rect(
							mouseStatus.getLeft(), 
							mouseStatus.getTop(), 
							mouseStatus.getWidth(), 
							mouseStatus.getHeight(),
							conf.getPointerThickness(),
							conf.getPointerColor()
						);
			nodes.add(rect);
			//System.out.println(mouseStatus.getX()+","+mouseStatus.getY());
		};
		public void whenMouseWheel(MouseWheelEvent e) {
			if(drawStatus == DRAW_TEXT) {
				
			}
			else {
				if(e.getWheelRotation() < 0) {
					conf.increasePointerThickness();
					setCursor(CursorManager.createCircleCursor(conf.getPointerThickness()));
				}
				else if(e.getWheelRotation() > 0){
					conf.decreasePointerThickness();
					setCursor(CursorManager.createCircleCursor(conf.getPointerThickness()));
				}
			}
		}
		public void whenMouseDragged(MouseEvent e) {
			log.debug("drag {}", e.getButton());
			//좌클릭 드래그
			log.debug("drawStatus = {}", drawStatus);
			switch(drawStatus) {
			case DRAW_RECT:
				flashNode = new Rect(
						mouseStatus.getLeft(), 
						mouseStatus.getTop(), 
						mouseStatus.getWidth(), 
						mouseStatus.getHeight(),
						conf.getPointerThickness(),
						conf.getPointerColor()
						);
				return;
			}
		}
	};

	public void init() {
		mouseStatus.setListener(mouseListener);
		addMouseMotionListener(mouseStatus);
		addMouseWheelListener(mouseStatus);
		addMouseListener(mouseStatus);
		delayManager.setTimeout(50L, ()->{
			super.prepare(conf.isPause() ? NOTE_MODE : NOTE_TRANSPARENT_MODE);//option
		});
	}
	
	public void open() {
		if(isVisible()) return;
		
		mouseStatus.setListener(mouseListener);
		setFrameMode(conf.isPause() ? NOTE_MODE : NOTE_TRANSPARENT_MODE);//option
		setScreenRect(screenManager.getCurrentMonitorRect());
		setCursor(CursorManager.createCircleCursor(conf.getPointerColor(), conf.getPointerThickness()));//option
		painter.setFps(24);//option
		painter.setListener(()->{
			repaint();
		});
		painter.start();
		setVisible(true);
	}
	
	@Override
	public void advancedPaint(Graphics2D g) {
		for(Node node : nodes) {
			g.drawImage(node.getImage(), node.getX(), node.getY(), node.getWidth(), node.getHeight(), this);
			//임시 도형
			if(flashNode != null) {
				g.drawImage(flashNode.getImage(), flashNode.getX(), flashNode.getY(), flashNode.getWidth(), flashNode.getHeight(), this);
			}
		}
	}

	@Override
	public void exit() {
		setVisible(false);
		super.exit();
	}

	@Override
	public void setKeyHook(GlobalKeyboardHook keyHook) {
		keyHook.addKeyListener(new GlobalKeyListener() {
			@Override
			public void keyReleased(GlobalKeyEvent event) {
				switch(event.getVirtualKeyCode()) {
				case GlobalKeyEvent.VK_F1: 
					conf.setPointerColor(conf.getF1Color());
					setCursor(CursorManager.createCircleCursor(conf.getF1Color(), conf.getPointerThickness()));
					break;
				case GlobalKeyEvent.VK_F2:
					conf.setPointerColor(conf.getF2Color());
					setCursor(CursorManager.createCircleCursor(conf.getF2Color(), conf.getPointerThickness()));
					break;
				case GlobalKeyEvent.VK_F3:
					conf.setPointerColor(conf.getF3Color());
					setCursor(CursorManager.createCircleCursor(conf.getF3Color(), conf.getPointerThickness()));
					break;
				case GlobalKeyEvent.VK_F4:
					conf.setPointerColor(conf.getF4Color());
					setCursor(CursorManager.createCircleCursor(conf.getF4Color(), conf.getPointerThickness()));
					break;
				case GlobalKeyEvent.VK_F5:
					conf.setPointerColor(conf.getF5Color());
					setCursor(CursorManager.createCircleCursor(conf.getF5Color(), conf.getPointerThickness()));
					break;
				case GlobalKeyEvent.VK_F6:
					conf.setPointerColor(conf.getF6Color());
					setCursor(CursorManager.createCircleCursor(conf.getF6Color(), conf.getPointerThickness()));
					break;
				case GlobalKeyEvent.VK_F7:
					conf.setPointerColor(conf.getF7Color());
					setCursor(CursorManager.createCircleCursor(conf.getF7Color(), conf.getPointerThickness()));
					break;
				case GlobalKeyEvent.VK_F8:
					conf.setPointerColor(conf.getF8Color());
					setCursor(CursorManager.createCircleCursor(conf.getF8Color(), conf.getPointerThickness()));
					break;
				case GlobalKeyEvent.VK_F9:
					conf.setPointerColor(conf.getF9Color());
					setCursor(CursorManager.createCircleCursor(conf.getF9Color(), conf.getPointerThickness()));
					break;
				case GlobalKeyEvent.VK_F10:
					conf.setPointerColor(conf.getF10Color());
					setCursor(CursorManager.createCircleCursor(conf.getF10Color(), conf.getPointerThickness()));
					break;
				case GlobalKeyEvent.VK_F11:
					conf.setPointerColor(conf.getF11Color());
					setCursor(CursorManager.createCircleCursor(conf.getF11Color(), conf.getPointerThickness()));
					break;
				case GlobalKeyEvent.VK_F12:
					conf.setPointerColor(conf.getF12Color());
					setCursor(CursorManager.createCircleCursor(conf.getF12Color(), conf.getPointerThickness()));
					break;
				case GlobalKeyEvent.VK_Q:
					drawStatus = DRAW_RECT;
					break;
				}
			}
			@Override
			public void keyPressed(GlobalKeyEvent event) {}
		});
	}

}

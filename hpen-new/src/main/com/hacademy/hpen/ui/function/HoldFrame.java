package com.hacademy.hpen.ui.function;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.hacademy.hpen.ui.DefaultFrame;
import com.hacademy.hpen.ui.event.MouseEventListener;
import com.hacademy.hpen.ui.event.MouseStatus;
import com.hacademy.hpen.ui.event.ScheduledThread;
import com.hacademy.hpen.ui.option.process.CaptureConfiguration;
import com.hacademy.hpen.util.clipboard.ClipboardManager;
import com.hacademy.hpen.util.delay.DelayManager;
import com.hacademy.hpen.util.image.ImageManager;
import com.hacademy.hpen.util.loader.annotation.Autowired;
import com.hacademy.hpen.util.loader.annotation.Component;

import lc.kra.system.GlobalHookMode;
import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import lc.kra.system.keyboard.event.GlobalKeyListener;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HoldFrame extends DefaultFrame{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private MouseStatus status;
	
	@Autowired
	private ClipboardManager clipboardManager;
	
	@Autowired
	private ImageManager imageManager;
	
	@Autowired
	private DelayManager delayManager;
	
	private ScheduledThread painter;
	
	@Autowired
	private CaptureConfiguration captureConfiguration;
	
	private MouseMotionListener dragListener = new MouseMotionAdapter() {
		private int x, y;
		public void mouseMoved(MouseEvent e) {
			x = e.getX();
			y = e.getY();
		}
		public void mouseDragged(MouseEvent e) {
			Point p = getLocation();
			p.x += e.getX() - x;
			p.y += e.getY() - y;
			setLocation(p);
		}
	};
	
	private MouseEventListener listener = new MouseEventListener() {
		public void whenMouseRelease(MouseEvent e) {
			//영역 계산
			int thickness = captureConfiguration.getBorderThickness();
			Rectangle rect = status.getRect();
			Rectangle rectOnScreen = status.getRectOnScreen();
			if(rect == null) return;
			log.debug("mouse release : rect = {}, rectOnScreen = {}", rect, rectOnScreen);
			rect.x += thickness;						rectOnScreen.x += thickness;
			rect.y += thickness;						rectOnScreen.y += thickness;
			rect.width -= thickness * 2;			rectOnScreen.width -= thickness * 2;
			rect.height -= thickness * 2;		rectOnScreen.height -= thickness * 2;
			
			//옵션별 처리
			if(rect.width > 0 && rect.height > 0) {
				setTransparent(false);
				setBounds(rect);
				addMouseMotionListener(dragListener);
				stop();
				
				delayManager.setTimeout(200L, ()->{
					setBg(screenManager.getImage(rectOnScreen));
					repaint();
				});
			}
		}
	};
	
	private GlobalKeyboardHook keyHook;
	
	public void open() {
		setTransparent(true);
		
		removeMouseMotionListener(dragListener);
		
		//keyboard event
		this.keyHook = new GlobalKeyboardHook(GlobalHookMode.DEFAULT);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				close();
			}
		});
		
		if(keyHook != null) {
			keyHook.addKeyListener(new GlobalKeyListener() {
				public void keyReleased(GlobalKeyEvent event) {}
				public void keyPressed(GlobalKeyEvent event) {
					if(event.getVirtualKeyCode() == GlobalKeyEvent.VK_ESCAPE) {
						close();
					}
				}
			});
		}
		else {
			addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					switch(e.getKeyCode()) {
					case KeyEvent.VK_ESCAPE:
						close();
					}
				}
			});
		}
		
		//mouse event
		addMouseMotionListener(status);
		addMouseListener(status);
		status.setListener(listener);
		
		//configuration loading
		stroke = new BasicStroke(captureConfiguration.getBorderThickness());
		
		//paint thread
		painter = new ScheduledThread();
		painter.setFps(24);
		painter.setListener(()->{
			repaint();
		});
		painter.start();
		setVisible(true);
	}
	public void stop() {
		removeMouseMotionListener(status);
		removeMouseListener(status);
		status.setListener(null);
		painter.kill();
	}
	public void close() {
		if(!this.isVisible()) return;
		
		log.debug("close frame");
		setVisible(false);
		stop();
	}
	
	/**
	 * Guide color 설정
	 */
	private Color mouseGuideColor = Color.black;
	private Color tempShapeBorderColor = Color.blue;
	private Color tempShapeAreaColor = new Color(0, 0, 0, 0);
	@Setter @Getter
	private Stroke stroke;
	
	@Override
	public void paintExtra(Graphics2D g) {
		log.debug("paintExtra");
		if(isTransparent()) {
			//필수
			if(g.getComposite() != AlphaComposite.Src) {
				g.setComposite(AlphaComposite.Src);
			}

			//stroke 설정
			if(g.getStroke() != stroke) {
				g.setStroke(stroke);
			}
			
			if(status.isDrag()) {
				//테두리 그리기
				g.setColor(tempShapeBorderColor);
				g.drawRect(status.getLeft(), status.getTop(), status.getWidth(), status.getHeight());
				
				//캡쳐영역 투명처리
				if(captureConfiguration.isTransparent()) {
					int thickness = captureConfiguration.getBorderThickness();
					g.setColor(tempShapeAreaColor);
					g.fillRect(
							status.getLeft() + thickness , 
							status.getTop() + thickness, 
							status.getWidth() - thickness * 2, 
							status.getHeight() - thickness * 2
						);
				}
			}
			else if(captureConfiguration.isGuideVisible()){
				g.setColor(mouseGuideColor);
				g.drawLine(status.getX(), 0, status.getX(), getHeight());
				g.drawLine(0, status.getY(), getWidth(), status.getY());
			}
		}
		else {
			g.drawImage(getBg(), 0, 0, getWidth(), getHeight(), this);
			g.drawRect(0, 0, getWidth()-1, getHeight()-1);
		}
	}
}

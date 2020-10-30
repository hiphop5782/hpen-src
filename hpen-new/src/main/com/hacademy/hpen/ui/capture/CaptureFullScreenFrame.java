package com.hacademy.hpen.ui.capture;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import com.hacademy.hpen.ui.MultiOptionFrame;
import com.hacademy.hpen.ui.event.MouseEventListener;
import com.hacademy.hpen.ui.event.MouseStatus;
import com.hacademy.hpen.ui.event.ScheduledThread;
import com.hacademy.hpen.ui.option.process.CaptureConfiguration;
import com.hacademy.hpen.util.clipboard.ClipboardManager;
import com.hacademy.hpen.util.cursor.CursorManager;
import com.hacademy.hpen.util.image.ImageManager;
import com.hacademy.hpen.util.loader.annotation.Autowired;
import com.hacademy.hpen.util.loader.annotation.Component;
import com.hacademy.hpen.util.screen.ImageType;
import com.hacademy.hpen.util.screen.ScreenManager;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CaptureFullScreenFrame extends MultiOptionFrame{
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private MouseStatus status;
	
	@Autowired
	private ScreenManager screenManager;
	
	@Autowired
	private ClipboardManager clipboardManager;
	
	@Autowired
	private ImageManager imageManager;
	
	@Autowired
	private ScheduledThread painter;
	
	@Autowired
	private CaptureConfiguration captureConfiguration;
	
	private MouseEventListener listener = new MouseEventListener() {
		public void whenMouseRelease(MouseEvent e) {
			//영역 계산
			int thickness = captureConfiguration.getBorderThickness();
			Rectangle rect = status.getRect();
			rect.x += thickness/2;
			rect.y += thickness/2;
			rect.width -= thickness;
			rect.height -= thickness;
			
			//옵션별 처리
			if(rect.width > 0 && rect.height > 0) {
				BufferedImage capture = screenManager.getImage(rect);
				
				try {
					switch(captureConfiguration.getCaptureAction()) {
					case CaptureConfiguration.SAVE_CLIPBOARD:
						clipboardManager.copyImageToClipboard(capture);
						break;
					case CaptureConfiguration.SAVE_TEMP_FILE:
						File dir = new File(captureConfiguration.getCaptureFileSavePath());
						if(!dir.isDirectory()) dir.delete();
						if(!dir.exists()) dir.mkdirs();
						String filename = captureConfiguration.getCaptureFilePrefix() + captureConfiguration.getCaptureFileSequenceWithFormat() + captureConfiguration.getCaptureFileExtension();
						File target = new File(dir, filename);
						imageManager.saveImageAsFile(capture, target, ImageType.valueOf(captureConfiguration.getCaptureFileType().toUpperCase()));
						captureConfiguration.plusCaptureFileSequence();
						break;
					case CaptureConfiguration.SAVE_AS_FILE:
						imageManager.saveImageAsWithDialog(capture, CaptureFullScreenFrame.this);
						break;
					}
				}
				catch(Exception err) {
					log.error("저장 오류 발생", err);
				}
			}
			exitProcess();
		}
	};
	
	/**
	 * Guide color 설정
	 */
	private Color mouseGuideColor;
	private Color tempShapeBorderColor;
	private Color tempShapeAreaColor = new Color(0,0,0,0);
	@Setter @Getter
	private Stroke stroke;
	
	public CaptureFullScreenFrame() {
		super(CAPTURE_TRANSPARENT_MODE);
	}
	
	public void init() {
		status.setListener(listener);
		addMouseMotionListener(status);
		addMouseListener(status);
		painter.setFps(24);
		painter.setListener(()->{
			repaint();
		});
	}
	
	public void open() {
		if(captureConfiguration.isPause()) { 
			setScreenRect(screenManager.getCurrentMonitorRect(), screenManager.getCurrentMonitorImage());
			setFrameMode(CAPTURE_PAUSE_MODE);
		}
		else { 
			setScreenRect(screenManager.getCurrentMonitorRect());
			setFrameMode(CAPTURE_TRANSPARENT_MODE);
		}
		mouseGuideColor = captureConfiguration.getMouseGuideColor();
		tempShapeBorderColor = captureConfiguration.getCaptureAreaColor();
		setStroke(new BasicStroke(captureConfiguration.getBorderThickness()));
		if(!captureConfiguration.isMouseVisible()) {
			setCursor(CursorManager.EMPTY_CURSOR);
		}
		painter.start();
		setVisible(true);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2d = (Graphics2D)g;
		
		//필수
		if(g2d.getComposite() != AlphaComposite.Src) {
			g2d.setComposite(AlphaComposite.Src);
		}

		//stroke 설정
		if(g2d.getStroke() != stroke) {
			g2d.setStroke(stroke);
		}
		
		if(status.isDrag()) {
			//테두리 그리기
			g2d.setColor(tempShapeBorderColor);
			g2d.drawRect(status.getLeft(), status.getTop(), status.getWidth(), status.getHeight());
			
			//캡쳐영역 투명처리
			g2d.setColor(tempShapeAreaColor);
			g2d.fillRect(status.getLeft(), status.getTop(), status.getWidth(), status.getHeight());
		}
		else if(captureConfiguration.isGuideVisible()){
			g2d.setColor(mouseGuideColor);
			g2d.drawLine(status.getX(), 0, status.getX(), getHeight());
			g2d.drawLine(0, status.getY(), getWidth(), status.getY());
		}
	}

	@Override
	public void exitProcess() {
		painter.kill();
		setVisible(false);
	}
	
}


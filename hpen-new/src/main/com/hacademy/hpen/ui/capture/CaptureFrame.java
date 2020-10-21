package com.hacademy.hpen.ui.capture;

import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import com.hacademy.hpen.util.cursor.CursorManager;
import com.hacademy.hpen.util.layer.MultipleLayerPanel;
import com.hacademy.hpen.util.loader.annotation.Autowired;
import com.hacademy.hpen.util.loader.annotation.Component;
import com.hacademy.hpen.util.screen.ScreenManager;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 *	스크린 캡쳐를 위한 프레임 
 *	모니터 영역 내에서 원하는 부분을 캡쳐할 수 있도록 구성
 * 
 * 	@author hiphop5782
 */
@Slf4j
@Component
public class CaptureFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	
	public CaptureFrame() {}
	
	@Autowired
	private ScreenManager screenManager;
	
	@Autowired
	private MultipleLayerPanel layerPanel;
	
	@Autowired
	private ScheduledThread painter;
	
	@Autowired
	private CaptureFrameMouseMotionListener mouseMotionListener;
	
	private ScheduledListener listener = ()->{
		int xpos = mouseMotionListener.getX();
		int ypos = mouseMotionListener.getY();
		layerPanel.refreshMouseLayer(xpos, ypos);
	};
	
	public void init() {
		panelSetting();
		frameSetting();
		cursorSetting();
		exitSetting();
		keyEventSetting();
		mouseEventSetting();
	}
	
	private void panelSetting() {
		setContentPane(layerPanel);
	}
	
	/**
	 * 프레임 초기 설정
	 */
	private void frameSetting() {
		setUndecorated(true);//타이틀바 삭제
		setAlwaysOnTop(true);//항상 위
		setResizable(false);//크기 조절 불가
//		setExtendedState(MAXIMIZED_BOTH);//최대 크기
		setFocusTraversalKeysEnabled(false);//탭키 잠금
	}
	
	/**
	 * 커서 설정 : 비어있는 커서로 설정
	 */
	private void cursorSetting() {
		getContentPane().setCursor(CursorManager.EMPTY_CURSOR);
	}
	
	/**
	 * 종료 설정
	 */
	private void exitSetting() {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);//종료 방지
		addWindowListener(windowListener);
	}
	/**
	 * Event Object
	 */
	private WindowListener windowListener = new WindowAdapter() {
		public void windowClosing(WindowEvent e) {
			finish();
		}
	};
	
	private KeyListener keyListener = new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_ESCAPE: finish(); return;
			}
		}
	};
	
	/**
	 * Keyboard Event 설정
	 * - ESC : 프레임 종료
	 */
	private void keyEventSetting() {
		addKeyListener(keyListener);
	}
	private void mouseEventSetting() {
		addMouseMotionListener(mouseMotionListener);
	}
	
	
	/**
	 * 시작 처리 기능
	 * - 화면 위치 설정
	 * - 배경 설정
	 * - 키 이벤트 방지 설정
	 * - 페인터 스레드 시작
	 * - 표시 설정
	 */
	@Setter @Getter
	private Integer screenNumber;
	public boolean isCurrentScreenCaptureMode() {
		return screenNumber == null;
	}
	
	public void start() {
//		Rectangle rect = screenManager.getCurrentMonitorRect();
		Rectangle rect = new Rectangle(0, 0, 300, 300);
		BufferedImage image = screenManager.getCurrentMonitorImage();
		layerPanel.setLayer(MultipleLayerPanel.BACKGROUND_LAYER, image);
		System.out.println(layerPanel.getLayerCount());
		start(rect);
	}
	public void start(int screen) {
		Rectangle rect = screenManager.getMonitorRect(screen);
		BufferedImage image = screenManager.getMonitorImage(screen);
		layerPanel.setLayer(MultipleLayerPanel.BACKGROUND_LAYER, image);
		setScreenNumber(screen);
		start(rect);
	}
	public void start(Rectangle rect) {
		setBounds(rect);
		setVisible(true);
		painter.setListener(listener);
		painter.start();
	}
	
	/**
	 * 종료 처리 기능
	 * - 창은 그대로 유지
	 * - 숨김 설정
	 * - 키 이벤트 방지 해제
	 * - 페인터 스레드 제거
	 * - 배경 제거
	 * - 마지막 사용 설정 저장
	 */
	public void finish() {
		painter.kill();
		setVisible(false);
	}
	
}

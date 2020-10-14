package com.hacademy.hpen.ui.capture;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.JFrame;

import com.hacademy.hpen.util.cursor.CursorManager;
import com.hacademy.hpen.util.loader.annotation.Autowired;
import com.hacademy.hpen.util.loader.annotation.Component;
import com.hacademy.hpen.util.screen.ScreenManager;

import lombok.Getter;
import lombok.Setter;

/**
 *	스크린 캡쳐를 위한 프레임 
 *	모니터 영역 내에서 원하는 부분을 캡쳐할 수 있도록 구성
 * 
 * 	@author hiphop5782
 */
@Component
public class CaptureFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	
	@Setter @Getter
	private int mode;
	
	@Autowired
	private ScreenManager screenManager;

	public CaptureFrame() {}
	
	public void init() {
		frameSetting();
		cursorSetting();
		exitSetting();
	}
	
	/**
	 * 프레임 초기 설정
	 */
	private void frameSetting() {
		setUndecorated(true);//타이틀바 삭제
		setAlwaysOnTop(true);//항상 위
		setResizable(false);//크기 조절 불가
		setExtendedState(MAXIMIZED_BOTH);//최대 크기
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
	}
	
	
	/**
	 * 시작 처리 기능
	 * - 화면 위치 설정
	 * - 배경 설정
	 * - 키 이벤트 방지 설정
	 * - 페인터 스레드 시작
	 * - 표시 설정
	 */
	public void start() {
		Rectangle rect = screenManager.getCurrentMonitorRect();
		start(rect);
	}
	public void start(int screen) {
		Rectangle rect = screenManager.getMonitorRect(screen);
		start(rect);
	}
	public void start(Rectangle rect) {
		setBounds(rect);
		setVisible(true);
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
		setVisible(false);
	}
	
	
	/**
	 * paint 기능
	 * - 더블 버퍼링 사용
	 */
	private Image background;
	private Graphics backgroundPen;
	@Override
	public void update(Graphics g) {
		paint(g);
	}
	@Override
	public void paint(Graphics g) {
		//더블 버퍼링을 위한 백그라운드 준비
		if(background == null) {
			background = createImage(getWidth(), getHeight());
			backgroundPen = background.getGraphics();
		}
		else {
			backgroundPen.clearRect(0, 0, getWidth(), getHeight());
		}
		//백그라운드에 그리기
		
		//백그라운드 이미지 화면에 복사
		g.drawImage(background, 0, 0, getContentPane());
	}
	
}

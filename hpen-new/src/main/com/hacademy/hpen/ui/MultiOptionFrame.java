package com.hacademy.hpen.ui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 *	<h1>추상 Frame</h1>
 *	추상 프레임은 다음의 처리를 수행할 수 있다.
 *	<ul>
 *		<li>전체 화면 프레임(FULLSCREEN_MODE)</li>
 *		<li>선택 영역 프레임(SELECTION_MODE)</li>
 *		<li>정지 화면 프레임(PAUSE_MODE)</li>
 *		<li>투명 화면 프레임(TRANSPARENT_MODE)</li>
 *	</ul>
 *	<p>
 *		
 * 	</p>
 */
@Slf4j
public abstract class MultiOptionFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 상태값
	 * - flag 방식을 사용
	 * - SELECTION_MODE와 FULLSCREE_MODE 동시적용 불가
	 * - PAUSE_MODE와 TRANSPARENT_MODE 동시적용 불가
	 */
	public static final int SELECTION_MODE = 0x1;
	public static final int FULLSCREEN_MODE = 0x2;
	public static final int PAUSE_MODE = 0x4;
	public static final int TRANSPARENT_MODE = 0x8;
	public static final int KEYPREVENT_MODE = 0x10;
	public static final int KEYPASS_MODE = 0x20;
	public static final int CAPTURE_MODE = FULLSCREEN_MODE | TRANSPARENT_MODE | KEYPREVENT_MODE;
	public static final int NOTE_MODE = FULLSCREEN_MODE | TRANSPARENT_MODE | KEYPREVENT_MODE;
	
	/**
	 * TRANSPARENT_MODE에서 사용할 색상
	 */
	@Setter @Getter
	private Color transparentColor = new Color(0,0,0,50);
	
	/**
	 * 화면 상태에 대한 설정값
	 * - 초기값은 전체화면에 정지화면 모드
	 */
	@Getter
	private int frameMode;
	public void setFrameMode(int frameMode) {
		if(checkBoth(frameMode, FULLSCREEN_MODE, SELECTION_MODE) 
				&& checkBoth(frameMode, PAUSE_MODE, TRANSPARENT_MODE)
				&& checkBoth(frameMode, KEYPREVENT_MODE, KEYPASS_MODE)) {
			this.frameMode = frameMode;
			return;
		}
		throw new IllegalArgumentException("잘못된 Frame mode 설정");
	}
	
	/**
	 * flag에 동시에 설정될 수 없는 값을 검증하는 메소드
	 * @param value flag값
	 * @param flag1 검증항목1
	 * @param flag2 검증항목2
	 * @return 정상설정 여부
	 */
	public boolean checkBoth(int value, int flag1, int flag2) {
		return ((value & flag1) == 0 && (value & flag2) != 0) || ((value & flag1) != 0 && (value & flag2) == 0);
	}
	
	/**
	 * 상태 확인 메소드
	 */
	public boolean is(int frameMode) {
		return (this.frameMode & frameMode) == frameMode;
	}
	
	/**
	 * 생성		
	 */
	public MultiOptionFrame(int frameMode) {
		setFrameMode(frameMode);
		frameSetting();
		eventSetting();
	}
	
	/**
	 * 	Display Rectangle 설정
	 */
	@Getter
	private Rectangle screenRect;
	protected void setScreenRect(Rectangle screenRect) {
		this.screenRect = screenRect;
		setBounds(screenRect);
	}
	
	
	/**
	 * 프레임 설정
	 * - FULLSCREEN_MODE : 전체화면 설정 및 드래그 금지
	 * - SELECTION_MODE : 전체화면 설정 후 영역 설정이 이루어지면 Rect 변경
	 * - TRANSPARENT_MODE : 배경 투명 설정
	 * - PAUSE_MODE : 배경 캡쳐 설정
	 */
	protected void frameSetting() {
		setUndecorated(true);
		setResizable(false);
		setFocusTraversalKeysEnabled(false);//탭키 잠금
		
		if(is(TRANSPARENT_MODE)) {//투명 배경 설정
			setBackground(transparentColor);
		}
		else {//배경 캡쳐 설정
			
		}
	}
	
	/**
	 * 이벤트 설정
	 */
	protected void eventSetting() {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exitProcess();
			}
		});
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_ESCAPE:
					exitProcess();
				}
			}
		});
	}
	
	/**
	 * 페인트 방식 정의
	 * - TRANSPARENT_MODE : 배경 투명 및 커서 처리
	 * - PAUSE_MODE : 배경 유지 및 커서 처리
	 */
	@Override
	public void paint(Graphics g) {
		if(is(TRANSPARENT_MODE))
			transparentPaint((Graphics2D)g);
		else
			pausePaint((Graphics2D)g);
	}
	
	/**
	 *	투명 화면 페인트 작업
	 */
	protected void transparentPaint(Graphics2D g) {
		if(g.getComposite() != AlphaComposite.Src)
			g.setComposite(AlphaComposite.Src);
		g.setColor(transparentColor);
		g.fillRect(0, 0, screenRect.width, screenRect.height);
	}
	
	/**
	 * 	정지 화면 페인트 작업
	 */
	protected void pausePaint(Graphics2D g) {
		
	}
	
	/**
	 * 종료 작업
	 */
	public abstract void exitProcess();
}







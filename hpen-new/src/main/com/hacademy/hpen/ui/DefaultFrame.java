package com.hacademy.hpen.ui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import com.hacademy.hpen.util.loader.annotation.Autowired;
import com.hacademy.hpen.util.screen.ScreenManager;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * <h1>기본 프레임</h1>
 *	프레임의 기본 설정을 손쉽게 구현할 수 있도록 설정하는 것에 초점을 맞추었다. 
 */
@Slf4j
public abstract class DefaultFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 배경의 투명 여부를 설정하는 변수
	 */
	@Setter @Getter
	private boolean transparent;
	
	/**
	 * 투명 설정 시 사용할 색상
	 */
	@Setter @Getter
	private Color transparentColor = new Color(0,0,0,1);
	
	@Autowired @Getter
	protected ScreenManager screenManager;
	
	/**
	 * 초기 생성 옵션
	 * - 타이틀 제거(undecorated)
	 * - 탭키 잠금
	 */
	public DefaultFrame() {
		setUndecorated(true);
		setAlwaysOnTop(true);
		setResizable(false);
		setBackground(transparentColor);
		setFocusTraversalKeysEnabled(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}
	
	/**
	 * 투명 처리용 이미지
	 */
	@Setter @Getter
	private BufferedImage bg;
	@Override
	public void setBounds(Rectangle r) {
		super.setBounds(r);
	}
	
	public void setFillCurrentMonitor() {
		if(!transparent)
			this.bg = screenManager.getCurrentMonitorImage();
		this.setBounds(screenManager.getCurrentMonitorRect());
	}
	
	/**
	 * paint
	 */
	@Override
	public void paint(Graphics g) {
		log.debug("paint call");
		if(transparent) {//투명
			Graphics2D g2d = (Graphics2D)g;
			transparentPaint(g2d);
			paintExtra(g2d);
		}
		else {//불투명
			Image im = createImage(getWidth(), getHeight());
			Graphics2D g2d = (Graphics2D)im.getGraphics();
			pausePaint(g2d);
			paintExtra(g2d);
			
			//double buffering
			g.drawImage(im, 0, 0, getWidth(), getHeight(), this);
		}
	}
	
	/**
	 *	투명 화면 페인트 작업
	 */
	protected void transparentPaint(Graphics2D g) {
		log.debug("transparent painting = {}, {}", g.getComposite(), g.getComposite() == AlphaComposite.Src);
		if(g.getComposite() != AlphaComposite.Src)
			g.setComposite(AlphaComposite.Src);
		g.setColor(transparentColor);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
	
	/**
	 * 	정지 화면 페인트 작업
	 */
	protected void pausePaint(Graphics2D g) {
		if(!g.getComposite().equals(AlphaComposite.Src)) {
			g.setComposite(AlphaComposite.Src);
		}
		g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
	}
	
	/**
	 * extra paint(필요시 재정의)
	 * @param g2d Graphics2D 객체
	 */
	public void paintExtra(Graphics2D g2d) {}
}





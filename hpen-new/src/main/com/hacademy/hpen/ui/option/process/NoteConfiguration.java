package com.hacademy.hpen.ui.option.process;

import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;

import com.hacademy.hpen.util.cursor.CursorManager;
import com.hacademy.hpen.util.loader.annotation.Autowired;
import com.hacademy.hpen.util.loader.annotation.Component;
import com.hacademy.hpen.util.object.ObjectCopyManager;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Component
@ToString(exclude= {"configurationManager", "objectCopyManager"})
public class NoteConfiguration implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private transient ConfigurationManager configurationManager;
	
	@Autowired
	private transient ObjectCopyManager objectCopyManager;
	
	/**
	 * 	배경 속성
	 * 	- background
	 * 		- transparent : 배경 투명 설정(기본값)
	 * 		- pause : 이미지로 고정 화면 처리
	 */
	public static final transient String TRANSPARENT = "transparent";
	public static final transient String PAUSE = "pause";
	private String background = "transparent";
	public void setBackground(String type) {
		switch(type.toLowerCase()) {
		case TRANSPARENT:
			background = TRANSPARENT; break;
		case PAUSE:
			background = PAUSE; break;
		}
		save();
	}
	public void setPause(boolean pause) {
		if(pause) background = PAUSE;
		else background = TRANSPARENT;
		save();
	}
	public boolean isPause() {
		return PAUSE.equals(background);
	}
	public boolean isTransparent() {
		return TRANSPARENT.equals(background);
	}
	
	
	/**
	 * 화면 포인터 옵션
	 */
	private Color f1Color = Color.black;
	private Color f2Color = Color.red;
	private Color f3Color = Color.blue;
	private Color f4Color = new Color(0xe6, 0x7e, 0x22);
	private Color f5Color = new Color(0x8e, 0x44, 0xad);
	private Color f6Color = new Color(0x27, 0xae, 0x60);
	private Color f7Color = new Color(0x2c, 0x3e, 0x50);
	private Color f8Color = new Color(0xe7, 0x4c, 0x3c);
	private Color f9Color = new Color(209, 204, 192);
	private Color f10Color = new Color(132, 129, 122);
	private Color f11Color = new Color(89, 98, 117);
	private Color f12Color = new Color(48, 57, 82);
	
	public void setF1Color(Color color) {
		f1Color = color;
		save();
	}
	public void setF2Color(Color color) {
		f2Color = color;
		save();
	}
	public void setF3Color(Color color) {
		f3Color = color;
		save();
	}
	public void setF4Color(Color color) {
		f4Color = color;
		save();
	}
	public void setF5Color(Color color) {
		f5Color = color;
		save();
	}
	public void setF6Color(Color color) {
		f6Color = color;
		save();
	}
	public void setF7Color(Color color) {
		f7Color = color;
		save();
	}
	public void setF8Color(Color color) {
		f8Color = color;
		save();
	}
	public void setF9Color(Color color) {
		f9Color = color;
		save();
	}
	public void setF10Color(Color color) {
		f10Color = color;
		save();
	}
	public void setF11Color(Color color) {
		f11Color = color;
		save();
	}
	public void setF12Color(Color color) {
		f12Color = color;
		save();
	}
	
	private Color pointerColor = Color.black;
	public void setPointerColor(Color color) {
		pointerColor = color;
		save();
	}
	
	private int pointerThickness = 5;
	public void setPointerThickness(int thickness) {
		if(thickness > CursorManager.CURSOR_MAXIMUM_SIZE
				|| thickness < CursorManager.CURSOR_MINIMUM_SIZE)
			return;
		pointerThickness = thickness;
		save();
	}
	public void increasePointerThickness() {
		setPointerThickness(getPointerThickness() + 1);
	}
	public void decreasePointerThickness() {
		setPointerThickness(getPointerThickness() - 1);
	}
	
	/**
	 * IME setting
	 */
	private boolean korean = true;
	public void setKorean(boolean korean) {
		this.korean = korean;
		save();
	}
	
	/**
	 * Font setting
	 */
	private Font font = new Font("", Font.PLAIN, 20);
	public void setFont(Font font) {
		this.font = font;
		save();
	}
	public void increaseFontSize() {
		Font newFont = new Font(font.getName(), font.getStyle(), font.getSize() + 1);
		setFont(newFont);
	}
	public void decreaseFontSize() {
		Font newFont = new Font(font.getName(), font.getStyle(), font.getSize() - 1);
		setFont(newFont);
	}
	public void setFontBold() {
		Font newFont = new Font(font.getName(), Font.BOLD, font.getSize());
		setFont(newFont);
	}
	public void setFontPlain() {
		Font newFont = new Font(font.getName(), Font.PLAIN, font.getSize());
		setFont(newFont);
	}
	
	public void init() {
		try {
			NoteConfiguration conf = configurationManager.load(getClass());
			objectCopyManager.copy(conf, this);
			log.debug("load = {}", conf);
			log.debug("copy = {}", this);
		}
		catch(Exception e) {
			log.error("NoteConfiguration 불러오기 실패", e);
		}
	}
	
	public void save() {
		try {
			configurationManager.save(this);
			log.debug("note configuration save complete");
		}
		catch(Exception e) {
			log.error("note configuration save error", e);
		}
	}
	
}

package com.hacademy.hpen.ui.option.process;

import java.awt.Color;
import java.io.File;
import java.io.Serializable;
import java.text.DecimalFormat;

import com.hacademy.hpen.util.loader.annotation.Autowired;
import com.hacademy.hpen.util.loader.annotation.Component;
import com.hacademy.hpen.util.object.ObjectCopyManager;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Component
@Getter
@Slf4j
@ToString(exclude= {"configurationManager", "objectCopyManager"})
public class CaptureConfiguration implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private transient ConfigurationManager configurationManager;
	
	@Autowired
	private transient ObjectCopyManager objectCopyManager;
	
	public CaptureConfiguration() {}

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
	 * 	영역 픽셀 표시 속성
	 * 	- pixel
	 * 		- show : 표시
	 * 		- hide : 숨김 
	 */
	public static final transient String SHOW = "show";
	public static final transient String HIDE = "hide";
	private String pixel = SHOW;
	public void setPixel(boolean pixel) {
		if(pixel) this.pixel = SHOW;
		else this.pixel = HIDE;
		save();
	}
	public boolean isPixelVisible() {
		return SHOW.equals(pixel);
	}
	
	/**
	 * 마우스 표시 여부
	 */
	private String mouse = HIDE;
	public void setMouse(boolean mouse) {
		if(mouse)
			this.mouse = SHOW;
		else
			this.mouse = HIDE;
		save();
	}
	public boolean isMouseVisible() {
		return SHOW.equals(mouse);
	}
	
	/**
	 * 마우스 가이드 표시 여부
	 */
	private String guide = SHOW;
	public void setGuide(boolean guide) {
		if(guide) this.guide = SHOW;
		else this.guide = HIDE;
		save();
	}
	public boolean isGuideVisible() {
		return SHOW.equals(guide);
	}
	
	/**
	 * 	화면 속성
	 * 	- borderThickness : 가이드 및 캡쳐영역 테두리 두께
	 * 		- 두껍게 : 5
	 * 		- 보통 : 3
	 * 		- 가늘게 : 1
	 * 	- mouseGuideColor : 마우스 보조선 색상
	 * 	- captureAreaColor : 캡쳐 영역 색상
	 */
	public static final transient int THIN = 1;
	public static final transient int NORMAL = 3;
	public static final transient int THICK = 5;
	private int borderThickness = NORMAL;
	public void setBorderThickness(int thickness) {
		if(between(thickness, 1, 10)) {
			borderThickness = thickness;
			save();
		}
	}
	public boolean isThin() {
		return borderThickness == THIN;
	}
	public boolean isNormal() {
		return borderThickness == NORMAL;
	}
	public boolean isThick() {
		return borderThickness == THICK;
	}
	
	private Color mouseGuideColor = Color.black;
	public void setMouseGuideColor(Color mouseGuideColor) {
		this.mouseGuideColor = mouseGuideColor;
		save();
	}

	private Color captureAreaColor = Color.blue;
	public void setCaptureAreaColor(Color captureAreaColor) {
		this.captureAreaColor = captureAreaColor;
		save();
	}
	
	/**
	 * 	캡쳐 속성
	 * 	- captureAction : 캡쳐 이후에 할 행동 유형
	 * 		- clipboard : 클립보드로 전송
	 * 		- saveTempFile : 자체 임시 파일로 저장
	 * 		- saveAsFile : 사용자가 저장할 파일명 선택
	 * 	- captureFileType : 캡쳐 저장 시 파일의 유형
	 * 		- png : PNG 파일
	 * 		- jpg : JPG 파일
	 * 	- captureFileSavePath : 캡쳐 파일 저장 시 저장 위치
	 * 	- captureFilePrefix : 캡쳐 파일 저장 시 접두사
	 * 	- captureFileSequence : 캡쳐 파일 시작번호
	 */
	public static final transient String SAVE_CLIPBOARD = "clipboard";
	public static final transient String SAVE_TEMP_FILE = "saveTempFile";
	public static final transient String SAVE_AS_FILE = "saveAsFile";
	private String captureAction = SAVE_CLIPBOARD;
	public void setCaptureAction(String action) {
		switch(action) {
		case SAVE_CLIPBOARD: captureAction = SAVE_CLIPBOARD; break;
		case SAVE_TEMP_FILE: captureAction = SAVE_TEMP_FILE; break;
		case SAVE_AS_FILE: captureAction = SAVE_AS_FILE; break;
		default:return;
		}
		save();
	}
	public boolean isSaveClipboard() {
		return SAVE_CLIPBOARD.equals(captureAction);
	}
	public boolean isSaveTempFile() {
		return SAVE_TEMP_FILE.equals(captureAction);
	}
	public boolean isSaveAsFile() {
		return SAVE_AS_FILE.equals(captureAction);
	}
	
	public static final transient String PNG = "png";
	public static final transient String JPG = "jpg";
	private String captureFileType = "png";
	public void setCaptureFileType(String captureFileType) {
		switch(captureFileType.toLowerCase()) {
		case PNG: this.captureFileType = PNG; break;
		case JPG: this.captureFileType = JPG; break;
		default: return;
		}
		save();
	}
	public String getCaptureFileType() {
		return captureFileType;
	}
	public String getCaptureFileExtension() {
		return "."+getCaptureFileType();
	}
	public String[] getAllCaptureFileType() {
		return new String[]{PNG, JPG};
	}
	public boolean isPng() {
		return PNG.equals(captureFileType);
	}
	public boolean isJpg() {
		return JPG.equals(captureFileType);
	}
	
	private String captureFileSavePath = System.getProperty("user.dir") + File.separator + "capture";
	public void setCaptureFileSavePath(String captureFileSavePath) {
		this.captureFileSavePath = captureFileSavePath;
		save();
	}
	
	private String captureFilePrefix = "capture";
	public void setConfigurationManager(ConfigurationManager configurationManager) {
		this.configurationManager = configurationManager;
		save();
	}
	public void setCaptureFilePrefix(String captureFilePrefix) {
		if(captureFilePrefix == null) captureFilePrefix = "";
		this.captureFilePrefix = captureFilePrefix;
		save();
	}
	
	private int captureFileSequence = 1;
	public void setCaptureFileSequence(int captureFileSequence) {
		if(captureFileSequence < 0) return;
		this.captureFileSequence = captureFileSequence;
		save();
	}
	public static final transient int SEQUENCE_DEFAULT_SIZE = 6;
	public String getCaptureFileSequenceWithFormat() {
		return getCaptureFileSequenceWithFormat(SEQUENCE_DEFAULT_SIZE);
	}
	public String getCaptureFileSequenceWithFormat(int size) {
		StringBuffer buffer = new StringBuffer();
		for(int i=0;i<size;i++) buffer.append("0");
		DecimalFormat fmt = new DecimalFormat(buffer.toString());
		return fmt.format(captureFileSequence);
	}
	public void plusCaptureFileSequence() {
		this.captureFileSequence++;
		save();
	}
	
	/**
	 * 숫자 구간 검사 메소드
	 */
	private boolean between(int v, int a, int b) {
		return v >= a && v <= b;
	}
	
	public void init() {
		try {
			CaptureConfiguration conf = configurationManager.load(getClass());
			objectCopyManager.copy(conf, this);
			log.debug("load = {}", conf);
			log.debug("copy = {}", this);
			save();
		}
		catch(Exception e) {
			log.error("CaptureConfiguration 불러오기 실패", e);
		}
	}
	
	public void save() {
		try {
			configurationManager.save(this);
			log.debug("capture configuration save complete");
		}
		catch(Exception e) {
			log.error("capture configuration save error", e);
		}
	}

}

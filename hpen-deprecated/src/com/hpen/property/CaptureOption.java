package com.hpen.property;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.Properties;

import com.hpen.util.ColorManager;

/**
 * 사진 캡쳐의 환경 설정 클래스
 * @author Hwang
 */
public class CaptureOption {
	//property name list
	public static final String livecapture = "hpen.capture.livecapture";
	public static final String borderThickness = "hpen.capture.border.thickness";
	public static final String borderColor = "hpen.capture.border.color";
	public static final String zoomSize = "hpen.capture.zoom.size";
	public static final String zoomRate = "hpen.capture.zoom.rate";
	public static final String saveClipboard = "hpen.capture.save.clipboard";
	public static final String saveLocation = "hpen.capture.save.location";
	public static final String prefix = "hpen.capture.save.prefix";
	public static final String sequence = "hpen.capture.save.sequence";
	
	//singleton setting
	private static CaptureOption instance = new CaptureOption();
	public static CaptureOption getInstance() {
		return instance;
	}
	private CaptureOption() {}
	
	//properties object
	private Properties options;
	public void setOptions(Properties options) {
		this.options = options;
	}
	public Properties getOptions() {
		return options;
	}
	
	public boolean isLiveCapture() {
		return Boolean.parseBoolean(options.getProperty(livecapture));
	}
	public void setLiveCapture(boolean live) {
		options.setProperty(livecapture, String.valueOf(live));
	}
	/**
	 * 캡쳐된 이미지를 클립보드에 저장할 것인지를 설정하는 항목
	 * true - 클립보드에 저장
	 * false - 파일로 저장
	 */
	public boolean isCopytoClipboard() {
		return Boolean.parseBoolean(options.getProperty(saveClipboard));
	}
	public void setCaptureCopytoClipboard(boolean isCaptureCopytoClipboard) {
		options.setProperty(saveClipboard, String.valueOf(isCaptureCopytoClipboard));
	}
	/**
	 * 캡쳐 영역을 의미하는 사각형의 테두리를 설정하는 항목
	 * 미설정시 기본값은 3
	 */
	public int getCaptureBorderThickness() {
		return Integer.parseInt(options.getProperty(borderThickness));
	}
	public void setCaptureBorderThickness(int captureBorderThickness) {
		options.setProperty(borderThickness, String.valueOf(captureBorderThickness));
	}
	/**
	 * 캡쳐 영역을 의미하는 사각형의 테두리 색상 문자열
	 */
	public String getCaptureBorderColorString() {
		return options.getProperty(borderColor);
	}
	public void setCaptureBorderColorString(String captureBorderColorString) {
		options.setProperty(borderColor, captureBorderColorString);
	}
	/**
	 * 캡쳐 영역을 의미하는 사각형의 테두리 색상 Color 객체
	 */
	public Color getCaptureBorderColor() {
		return ColorManager.createColor(options.getProperty(borderColor));
	}
	/**
	 * 라이브 줌의 배율, 1~10배율로 설정 가능
	 * 기본값은 5
	 */
	public int getZoomrate() {
		return Integer.parseInt(options.getProperty(zoomRate));
	}
	public void setZoomrate(int zoomrate) {
		options.setProperty(zoomRate, String.valueOf(zoomrate));
	}
	/**
	 * 라이브 줌의 표시 크기, 기본값은 200 ( 200 x 200 )
	 */
	public int getZoomsize() {
		return Integer.parseInt(options.getProperty(zoomSize));
	}
	public void setZoomsize(int zoomsize) {
		options.setProperty(zoomSize, String.valueOf(zoomsize));
	}
	/**
	 * 라이브 줌의 좌상단 / 우하단 여백
	 */
	public static final int zoomgap = 20;
	
	public String getSaveFolder() {
		return options.getProperty(saveLocation);
	}
	public void setSaveFolder(String saveFolder) {
		options.setProperty(saveLocation, saveFolder);
	}
	
	public void setPrefix(String prefix) {
		options.setProperty(CaptureOption.prefix, prefix);
	}
	
	public String getPrefix() {
		return options.getProperty(prefix, "capture");
	}
	
	public void setSequence(int seq) {
		options.setProperty(sequence, String.valueOf(seq));
	}
	
	public int getSequence() {
		try {
			return Integer.parseInt(options.getProperty(sequence, "1"));
		}
		catch(Exception e) {
			return 1;
		}
	}
	public String getNextSequence() {
		return String.valueOf(getSequence()+1);
	}
	public void plusSequence() {
		setSequence(getSequence()+1);
	}
	
	public String getSequenceString(int size) {
		StringBuffer buffer = new StringBuffer();
		for(int i=0; i < size; i++) buffer.append("0");
		Format f = new DecimalFormat(buffer.toString());
		return f.format(getSequence());
	}
	
}










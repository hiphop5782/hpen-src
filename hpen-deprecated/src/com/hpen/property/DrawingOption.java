package com.hpen.property;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.hpen.Starter;
import com.hpen.util.ColorManager;
import com.hpen.util.CursorManager;
import com.hpen.value.DrawingConstants;

public class DrawingOption {
	//property list
	public static final String saveLocation = "hpen.draw.save.location";
	public static final String pointThickness = "hpen.draw.point.thickness";
	public static final String pointColor = "hpen.draw.point.color";
	public static final String pointColor1 = "hpen.draw.point.color1";
	public static final String pointColor2 = "hpen.draw.point.color2";
	public static final String pointColor3 = "hpen.draw.point.color3";
	public static final String pointColor4 = "hpen.draw.point.color4";
	public static final String pointColor5 = "hpen.draw.point.color5";
	public static final String pointColor6 = "hpen.draw.point.color6";
	public static final String pointColor7 = "hpen.draw.point.color7";
	public static final String pointColor8 = "hpen.draw.point.color8";
	public static final String pointColor9 = "hpen.draw.point.color9";
	public static final String pointColor0 = "hpen.draw.point.color0";
	public static final String textSize = "hpen.draw.text.size";
	public static final String fontFamily = "hpen.draw.text.family";
	public static final String korean = "hpen.draw.text.korean";
	public static final String iconSize = "hpen.draw.icon.size";
	
	//singleton setting
	private static DrawingOption instance = new DrawingOption();
	public static DrawingOption getInstance() {
		return instance;
	}
	private DrawingOption() {}
	
	//property
	private Properties options;
	public void setOptions(Properties options) {
		this.options = options;
	}
	public Properties getOptions() {
		return options;
	}
	
	public void initialize() {
		setPointColor(ColorManager.createColor(options.getProperty(pointColor)));
	}
	
	public void setKorean(boolean value){
		options.setProperty(korean, String.valueOf(value));
	}
	public boolean isKorean(){
		return Boolean.parseBoolean(options.getProperty(korean));
	}

	public void setPointThickness(int thickness){
		if(thickness > CursorManager.CURSOR_MAXIMUM_SIZE)
			thickness = CursorManager.CURSOR_MAXIMUM_SIZE;
		if(thickness < CursorManager.CURSOR_MINIMUM_SIZE)
			thickness = CursorManager.CURSOR_MINIMUM_SIZE;
		options.setProperty(pointThickness, String.valueOf(thickness));
	}
	public void increasePointThickness(){
		setPointThickness(getPointThickness()+1);
	}
	public void decreasePointThickness(){
		setPointThickness(getPointThickness()-1);
	}
	public int getPointThickness(){
		return Integer.parseInt(options.getProperty(pointThickness));
	}
	
	private Color pColor;
	public void setPointColor(Color color){
		options.setProperty(pointColor, ColorManager.getColorName(color));
		pColor = color;
	}
	public void setPointColor(int index) {
		Color color = getPointColor(index);
		setPointColor(color);
	}
	public Color getPointColor(){
		return pColor;
	}

	public Color getPointColor(int i) {
		String cName = options.getProperty(pointColor.substring(0, pointColor.length())+i);
		return ColorManager.createColor(cName);
	}
	
	public void setPointColor(int j, Color color) {
		options.setProperty(pointColor.substring(0, pointColor.length())+j, ColorManager.getColorName(color));
	}
	
	public Color getPointColorCopy() {
		return new Color(pColor.getRed(), pColor.getGreen(), pColor.getBlue());
	}
	
	public void setIconSize(int size) {
		if(size > CursorManager.ICON_MAXIMUM_SIZE)
			size = CursorManager.ICON_MAXIMUM_SIZE;
		if(size < CursorManager.ICON_MINIMUM_SIZE)
			size = CursorManager.ICON_MINIMUM_SIZE;
		options.setProperty(iconSize, String.valueOf(size));
	}
	public int getIconSize() {
		return Integer.parseInt(options.getProperty(iconSize));
	}
	public void increaseIconSize() {
		setIconSize(getIconSize()+1);
	}
	public void decreaseIconSize() {
		setIconSize(getIconSize()-1);
	}
	
	public void setFontfamily(String fontName){
		try{
			options.setProperty(fontFamily, URLEncoder.encode(fontName, "UTF-8"));
		}catch(Exception e){}
	}
	public String getFontfamily(){
		try {
			return URLDecoder.decode(options.getProperty(fontFamily), "UTF-8");
		}catch(Exception e) {
			return null;
		}
	}
	public void setFontSize(int font_size){
		options.setProperty(textSize, String.valueOf(font_size));
	}
	
	public Font getFont(){
		return new Font(getFontfamily(), Font.PLAIN, getFontSize());
	}
	public Font getFontCopy(){
		return new Font(getFontfamily(), Font.PLAIN, getFontSize());
	}
	public void increaseFontSize(){
		setFontSize(getFontSize()+2);
	}
	public void decreaseFontSize(){
		if(getFontSize() > 5){
			setFontSize(getFontSize()-2);
		}
	}
	public int getFontSize(){
		return Integer.parseInt(options.getProperty(textSize));
	}
	
	public void setSaveFolder(File saveFolder){
		try {
			options.setProperty(saveLocation, URLEncoder.encode(saveFolder.getAbsolutePath(), "UTF-8"));
		}catch(Exception e) {}
	}
	public File getSaveFolder(){
		try {
			String fname = URLDecoder.decode(options.getProperty(saveLocation), "UTF-8");
			return new File(fname);
		}catch(Exception e) {
			return new File(".");
		}
	}

	public void changeKorean() {
		setKorean(!isKorean());
	}
	
}

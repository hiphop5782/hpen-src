package com.hpen.property;

import java.awt.Rectangle;
import java.util.Properties;

public class ZoomOption {
	
	//property name list
	public static final String zoomWidth = "hpen.zoom.width";
	public static final String zoomHeight = "hpen.zoom.height";
	public static final String zoomX = "hpen.zoom.x";
	public static final String zoomY = "hpen.zoom.y";
	public static final String zoomRate = "hpen.zoom.rate";
	
	//singleton
	private static ZoomOption instance = new ZoomOption();
	public static ZoomOption getInstance() {
		return instance;
	}
	private ZoomOption() {}
	
	//properties object
	private Properties options;
	public void setOptions(Properties options) {
		this.options = options;
	}
	public Properties getOptions() {
		return options;
	}
	
	public int getX() {
		return Integer.parseInt(options.getProperty(zoomX));
	}
	public void setX(int x) {
		options.setProperty(zoomX, String.valueOf(x));
	}
	public int getY() {
		return Integer.parseInt(options.getProperty(zoomY));
	}
	public void setY(int y) {
		options.setProperty(zoomY, String.valueOf(y));
	}
	public int getWidth() {
		return Integer.parseInt(options.getProperty(zoomWidth));
	}
	public void setWidth(int width) {
		options.setProperty(zoomWidth, String.valueOf(width));
	}
	public int getHeight() {
		return Integer.parseInt(options.getProperty(zoomHeight));
	}
	public void setHeight(int height) {
		options.setProperty(zoomHeight, String.valueOf(height));
	}

	public Rectangle getBounds(){
		return new Rectangle(getX(), getY(), getWidth(), getHeight());
	}

	public int getZoom() {
		return Integer.parseInt(options.getProperty(zoomRate));
	}

	public void setZoom(int zoom) {
		if(zoom < 1) zoom = 1;
		if(zoom > 10) zoom = 10;
		options.setProperty(zoomRate, String.valueOf(zoom));
	}
}

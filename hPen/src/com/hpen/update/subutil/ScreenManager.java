package com.hpen.update.subutil;

import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Rectangle;
import java.awt.SecondaryLoop;

/**
 * 화면을 제어하는 기능을 가진 Manager<br>
 * 싱글톤 방식으로 구현
 * @author User
 */
public class ScreenManager {

	private static ScreenManager manager = new ScreenManager();
	public static ScreenManager getManager() {
		return manager;
	}
	private ScreenManager() {}
	
	/**
	 * 
	 * @return 현재 커서가 위치한 모니터의 Rectangle정보
	 */
	public Rectangle getCurrentMonitorRect() {
		PointerInfo info = MouseInfo.getPointerInfo();
		GraphicsDevice device = info.getDevice();
		GraphicsConfiguration configuration = device.getDefaultConfiguration();
		return configuration.getBounds();
	}
	
	/**
	 * @return 모든 GraphicDevice 배열 정보
	 */
	private GraphicsDevice[] getAllGraphicDevices(){
		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] devices = environment.getScreenDevices();
		return devices;
	}
	
	/**
	 * 
	 * @return 모든 GraphicDevice 카운트 수
	 */
	public int getMonitorCount(){
		return getAllGraphicDevices().length;
	}
	
	/**
	 * 
	 * @return 모든 GraphicDevice의 Dimension 정보
	 */
	public Rectangle[] getAllMonitorsize(){
		GraphicsDevice[] devices = getAllGraphicDevices();
		Rectangle[] rect = new Rectangle[devices.length];
		for(int i=0; i < rect.length ; i++){
			rect[i] = devices[i].getDefaultConfiguration().getBounds(); 
		}
		return rect;
	}
	
	public static final int MAIN_MONITOR = 0;
	public static final int SECOND_MONITOR = 1;
	public static final int THIRD_MONITOR = 2;
	/**
	 * 원하는 화면에 대한 크기정보를 알아낼 수 있는 메소드
	 * @param screen 화면 번호
	 * @return 해당 모니터의 Rectangle 정보
	 */
	public Rectangle getMonitorRect(int screen){
		return getAllMonitorsize()[screen];
	}
	
}










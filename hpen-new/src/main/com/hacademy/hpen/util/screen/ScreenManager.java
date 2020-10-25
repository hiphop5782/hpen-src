package com.hacademy.hpen.util.screen;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.hacademy.hpen.util.loader.annotation.Component;

/**
 * 화면을 제어하는 기능을 가진 Manager
 * @author hiphop5782
 */
@Component
public class ScreenManager {
	private Robot r;
	public ScreenManager() {}
	
	public void init() {
		try {
			r = new Robot();
		}
		catch(Exception e) {
			System.err.println("create robot error");
		}
	}

	public BufferedImage getCurrentMonitorImage() {
		return r.createScreenCapture(getCurrentMonitorRect());
	}
	
	/**
	 * 현재 모니터의 이미지를 타입에 맞게 byte[] 로 반환하는 메소드
	 * @param hint 이미지 유형
	 * @return 이미지 데이터(byte)
	 * @throws IOException ImageIO.write()에서 발생하는 예외
	 */
	public byte[] getCurrentMonitorImageData(ImageType hint) throws IOException {
		BufferedImage image = getCurrentMonitorImage();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(image, hint.getValue(), out);
		return out.toByteArray();
	}
	
	public byte[] getCurrentMonitorImageDataAsJpg() throws IOException {
		return getCurrentMonitorImageData(ImageType.JPG);
	}
	
	public byte[] getCurrentMonitorImageDataAsPng() throws IOException {
		return getCurrentMonitorImageData(ImageType.PNG);
	}
	
	/**
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
	
	public BufferedImage getMonitorImage(int screen) {
		return r.createScreenCapture(getMonitorRect(screen));
	}
	
	public byte[] getMonitorImageData(int screen, ImageType hint) throws IOException {
		BufferedImage image = getMonitorImage(screen);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(image, hint.getValue(), out);
		return out.toByteArray();
	}
	
	public byte[] getMonitorImageDataAsJpg(int screen) throws IOException {
		return getMonitorImageData(screen, ImageType.JPG);
	}
	
	public byte[] getMonitorImageDataAsPng(int screen) throws IOException {
		return getMonitorImageData(screen, ImageType.PNG);
	}
	
	public BufferedImage getImage(Rectangle rect) {
		return r.createScreenCapture(rect);
	}
	
}










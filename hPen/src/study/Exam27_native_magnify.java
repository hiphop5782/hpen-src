package study;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import javax.imageio.ImageIO;

class MyHook{
	static {
		System.load(new File("dll/MyHook.dll").getAbsolutePath());
//		System.load(new File("dll/MyHook32.dll").getAbsolutePath());
//		System.load(new File("dll/MyHook64.dll").getAbsolutePath());
	}
	public static native void installHook();
	public static native void messages();
	public static native double getZoom();
	public static native int getX();
	public static native int getY();
	public static native int getWidth();
	public static native int getHeight();
	public static native byte[] getScreenshotForMagnifyScreen();
	public static Rectangle getRect() {
		return new Rectangle(getX(), getY(), getWidth(), getHeight());
	}
	public static BufferedImage getScreenshotImageForMagnifyScreen() throws IOException{
		byte[] by = getScreenshotForMagnifyScreen();
		InputStream in = new ByteArrayInputStream(by);
		BufferedImage bImage = ImageIO.read(in);
		return bImage;
	}
}

public class Exam27_native_magnify {
	public static void main(String[] args){
		MyHook.installHook();
		Runnable r = ()->{
			try {
				while(true) {
					double zoom = MyHook.getZoom();
					Rectangle rect = MyHook.getRect();
					System.out.println(rect+"zoom = "+zoom);
					
//					byte[] b = MyHook.getScreenshotForMagnifyScreen();
//					System.out.println(Arrays.toString(b));
					//BufferedImage image = MyHook.getScreenshotImageForMagnifyScreen();
					//System.out.println(image);
					
					try {Thread.sleep(1000);}catch(Exception e) {}
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		};
		new Thread(r).start();
		MyHook.messages();
	}
}

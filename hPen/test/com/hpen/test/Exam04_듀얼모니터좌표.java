package com.hpen.test;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Exam04_듀얼모니터좌표 {
	public static void main(String[] args) throws Exception{
//		while(true){
//			PointerInfo info = MouseInfo.getPointerInfo();
//			System.out.println(info.getLocation());
//			Thread.sleep(50);
//		}
		
		Robot r = new Robot();
		BufferedImage b = r.createScreenCapture(new Rectangle(1920, 30, 1680, 1050));
		File f = new File("capture.jpg");
		ImageIO.write(b, "jpg", f);
	}
}

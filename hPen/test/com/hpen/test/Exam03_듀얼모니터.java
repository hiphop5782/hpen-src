package com.hpen.test;

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Exam03_듀얼모니터 {
	public static void main(String[] args) {
//		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
//		System.out.println(screen);
//		
//		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//		GraphicsDevice[] devices = ge.getScreenDevices();
//		System.out.println("���� ����� ���� : "+devices.length);
//		
//		for(int i = 0; i<devices.length; i++){
//			GraphicsDevice dev = devices[i];
//			GraphicsConfiguration[] configs = dev.getConfigurations();
//			for(GraphicsConfiguration config : configs){//�Ƚᵵ ����. �⺻������ �� 0���濡 ��ġ
//				System.out.println(config.getBounds());
//				JFrame f = new JFrame(config);
//				f.setUndecorated(true);
//				f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//				dev.setFullScreenWindow(f);
//				f.setBounds(config.getBounds());
//				f.setVisible(true);
//				try{
//					Robot robot = new Robot();
//					BufferedImage img = robot.createScreenCapture(f.getBounds());
//					File file = new File("capture"+i+".jpg");
//					System.out.println(file);
//					System.out.println(ImageIO.write(img, "jpg", file));
//				}catch(Exception e){
//					e.printStackTrace();
//				}
//			}
//		}
		
		GraphicsDevice[] gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
		System.out.println(GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint());
		for(GraphicsDevice dev : gd){
			System.out.println("=============[display info]================");
			System.out.println(dev.getDisplayMode().getWidth()+", "+dev.getDisplayMode().getHeight());
			System.out.println(dev.getConfigurations()[0].getBounds().x > 0);
		}
		
		
	}
}







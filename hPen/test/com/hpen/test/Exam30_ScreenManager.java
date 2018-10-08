package com.hpen.test;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;

import javax.swing.JFrame;

import com.hpen.update.subutil.ScreenManager;

public class Exam30_ScreenManager {
	public static void main(String[] args) throws IOException {
		ScreenManager manager = ScreenManager.getManager();
//		System.out.println(manager.getCurrentMonitorRect());
		
		for(Rectangle rect : manager.getAllMonitorsize()) {
			System.out.println(rect);
		}
		
//		ImageIO.write(manager.getCurrentMonitorImage(), "png", new File("temp.png"));
		
//		JFrame frame = new JFrame() {
//			@Override
//			public void paint(Graphics g) {
//				g.drawImage(manager.getCurrentMonitorImage(), 
//						manager.getCurrentMonitorRect().x,
//						manager.getCurrentMonitorRect().y, 
//						manager.getCurrentMonitorRect().width, 
//						manager.getCurrentMonitorRect().height, 
//						this);
//				}
//		};
//		frame.setBounds(manager.getCurrentMonitorRect());
//		frame.setUndecorated(true);
//		frame.setVisible(true);
//		frame.repaint();
	}
}










package com.hpen.test;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JFrame;

import com.hakademy.utility.screen.ScreenManager;

public class Exam30_ScreenManager {
	public static void main(String[] args) throws IOException {
		ScreenManager manager = ScreenManager.getManager();
//		System.out.println(manager.getCurrentMonitorRect());
		
		for(Rectangle rect : manager.getAllMonitorsize()) {
			System.out.println(rect);
		}
		
//		ImageIO.write(manager.getCurrentMonitorImage(), "png", new File("temp.png"));
		
		JFrame frame = new JFrame() {
			@Override
			public void paint(Graphics g) {
				g.drawImage(manager.getCurrentMonitorImage(), 
						manager.getCurrentMonitorRect().x,
						manager.getCurrentMonitorRect().y, 
						manager.getCurrentMonitorRect().width, 
						manager.getCurrentMonitorRect().height, 
						this);
				}
		};
		frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				System.exit(0);
			}
		});
		frame.setBounds(manager.getCurrentMonitorRect());
		frame.setUndecorated(true);
		frame.setVisible(true);
		frame.repaint();
	}
}










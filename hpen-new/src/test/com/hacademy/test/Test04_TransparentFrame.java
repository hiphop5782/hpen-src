package com.hacademy.test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import org.junit.Test;

public class Test04_TransparentFrame {
	@Test
	public void test() throws Exception{
		JFrame frame = new JFrame() {
			int x = 0, y = 0;
			boolean flag = false;
			@Override
			public void paint(Graphics g) {
				System.out.println(getBackground());
				g.setColor(getBackground());
//				g.setColor(Color.black);
				g.drawLine(x, 0, x, getWidth());
				g.drawLine(0, y, getHeight(), y);
				if(flag) {
					x++; y++;
					if(x > 500 || y > 500) flag = false;
				}
				else {
					x--; y--;
					if(x < 0 || y < 0) flag = true;
				}
			}
		};
		frame.setBounds(0,0,500,500);
		frame.setResizable(false);
		frame.setUndecorated(true);
		frame.setBackground(new Color(0,0,0,122));
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
					System.exit(0);
			}
		});
		frame.setVisible(true);
		while(true) {
			frame.repaint();
			Thread.sleep(100);
		}
//		Thread.currentThread().join();
	}
}







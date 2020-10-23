package com.hacademy.test;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Test04_TransparentFrame {
	static class CustomFrame extends JFrame{
		int x = 0, y = 0;
		boolean flag = false;
		BufferedImage bg;
		CustomFrame(){
			setResizable(false);
			setUndecorated(true);
			setExtendedState(MAXIMIZED_BOTH);
			setBackground(new Color(0,0,0,122));
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			addMouseMotionListener(new MouseAdapter() {
				@Override
				public void mouseMoved(MouseEvent e) {
					x = e.getX();
					y = e.getY();
				}
			});
			addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
						System.exit(0);
				}
			});
			setVisible(true);
		}
		@Override
		public void paint(Graphics g) {
			if(bg == null) {
				bg = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
			}
			Graphics2D g2d = (Graphics2D)bg.getGraphics();
			g2d.clearRect(0, 0, getWidth(), getHeight());
			g2d.setComposite(AlphaComposite.Clear);
			g2d.fillRect(0, 0, getWidth(), getHeight());
			g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
			g.setColor(Color.black);
			g.drawLine(x, 0, x, getWidth());	
			g.drawLine(0, y, getHeight(), y);
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		CustomFrame frame = new CustomFrame();
		while(true) {
			frame.repaint();
			Thread.sleep(100);
		}
	}
}







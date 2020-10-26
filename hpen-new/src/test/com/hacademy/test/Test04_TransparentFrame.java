package com.hacademy.test;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import org.junit.Test;

public class Test04_TransparentFrame {
	int x = -1, y = -1;
	Color color = new Color(0,0,0,100);
	@Test
	public void test() throws Exception{
		MouseMotionListener listener = new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				x = e.getX();
				y = e.getY();
			}
		};
		JFrame frame = new JFrame() {
			@Override
			public void paint(Graphics g) {
				if(x < 0 || x > 500 || y < 0 || y > 500) return;
				BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
				Graphics2D g2 = image.createGraphics();
				
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
//				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//				g2.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
//				g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
//				g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
				g2.setColor(Color.black);
				g2.drawLine(x, 0, x, getWidth());
				g2.drawLine(0, y, getHeight(), y);
				g2.dispose();
				g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
			}
		};
		frame.setBounds(0,0,500,500);
		frame.setResizable(false);
		frame.setUndecorated(true);
		frame.setBackground(color);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
					System.exit(0);
			}
		});
		frame.setVisible(true);
		frame.addMouseMotionListener(listener);
		while(true) {
			frame.repaint();
			Thread.sleep(100);
		}
//		Thread.currentThread().join();
	}
}







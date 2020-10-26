package com.hacademy.test;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

public class Test04_TransparentFrame {
	static class CustomFrame extends JFrame{
		int x = 0, y = 0, dx = 0, dy = 0;
		boolean flag = false;
		Color transparent = new Color(0, 0, 0, 50);
		CustomFrame(){
			setResizable(false);
			setUndecorated(true);
			setExtendedState(MAXIMIZED_BOTH);
			setBackground(transparent);
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			addMouseMotionListener(new MouseAdapter() {
				@Override
				public void mouseMoved(MouseEvent e) {
					x = e.getX();
					y = e.getY();
					dx = dy = 0;
				}
				@Override
				public void mouseDragged(MouseEvent e) {
					dx = e.getX();
					dy = e.getY();
					System.out.println("x = " + x + ", y = " + y + ", dx = "+dx+", dy = " + dy);
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
//			Graphics2D g2d = (Graphics2D)g.create();
			Graphics2D g2d = (Graphics2D)g;
			g2d.setComposite(AlphaComposite.Src);
			g2d.setColor(transparent);
			g2d.fillRect(0, 0, getWidth(), getHeight());
			
			if(x < dx && y < dy) {
				g2d.setColor(Color.blue);
				g2d.drawRect(x, y, Math.abs(dx-x), Math.abs(dy-y));
			}
			else {
				g2d.setColor(Color.black);
				g2d.drawLine(x, 0, x, getHeight());
				g2d.drawLine(0, y, getWidth(), y);
			}
			
			g2d.dispose();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		CustomFrame frame = new CustomFrame();
		while(true) {
			frame.repaint();
			Thread.sleep(1000/30);
		}
	}
}







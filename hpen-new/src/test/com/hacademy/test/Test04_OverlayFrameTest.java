package com.hacademy.test;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Test04_OverlayFrameTest {
	static class OverlayFrame extends JFrame{
		JPanel panel = new JPanel();
		public OverlayFrame() {
			setBounds(0, 0, 300, 300);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setResizable(false);
			setVisible(true);
		}
		@Override
		public void update(Graphics g) {
			paint(g);
		}
		@Override
		public void paint(Graphics g) {
			
			g.fillRect(0, 0, getWidth(), getHeight());
		}
	}
	
	public static void main(String[] args) {
		OverlayFrame frame = new OverlayFrame();
	}
}

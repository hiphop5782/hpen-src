package com.hpen.test;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.QuadCurve2D;

import javax.swing.JFrame;

public class Exam14_부드러운커브 {
	public static void main(String[] args) {
		JFrame frame = new JFrame(){
			@Override
			public void update(Graphics g) {
				super.update(g);
			}
			@Override
			public void paint(Graphics g) {
				Graphics2D g2d = (Graphics2D)g;
				QuadCurve2D q = new QuadCurve2D.Float();
				q.setCurve(100, 100, 200, 200, 300, 300);
				g2d.draw(q);
			}
		};
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 800);
		frame.setVisible(true);
	}
}

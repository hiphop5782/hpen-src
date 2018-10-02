package com.hpen.test;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Exam33_드래그성능향상 {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			long lastTime = System.currentTimeMillis();
			
			@Override
			public void run() {
				JFrame frame = new JFrame("TestFrame");
				frame.setSize(600, 600);
				JPanel content = new JPanel(new BorderLayout());

				final JLabel mousePosition = new JLabel("Unknown");
				content.add(mousePosition, BorderLayout.NORTH);

				content.addMouseMotionListener(new MouseMotionAdapter() {
					@Override
					public void mouseMoved(MouseEvent e) {
						mousePosition.setText("X: " + e.getX() + " Y: " + e.getY());
						long currentTime = System.currentTimeMillis();
						System.out.println("차이 : "+(currentTime - lastTime));
						lastTime = currentTime;
					}
				});
				frame.setContentPane(content);
				frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//				frame.pack();
				frame.setVisible(true);
			}
		});
	}
}

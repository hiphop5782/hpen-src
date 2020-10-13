package com.hpen.test;

import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import com.tulskiy.keymaster.common.Provider;

public class Exam28_한영키감지 {
	public static void main(String[] args) {
//		Java로 안됨
//		JFrame frame = new JFrame();
//		frame.setBounds(100, 100, 500, 500);
//		
//		JTextArea field = new JTextArea();
//		JScrollPane pane = new JScrollPane(field);
//		field.setFont(new Font("", Font.PLAIN, 30));
//		
//		frame.getContentPane().add(pane);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		field.addKeyListener(new KeyAdapter() {
//			@Override
//			public void keyTyped(KeyEvent e) {
//				System.out.println("event");
//			}
//		});
//		
//		frame.setVisible(true);
		
		Provider provider = Provider.getCurrentProvider(true);
		provider.register(KeyStroke.getKeyStroke(KeyEvent.VK_KANA, 0), e->{
			System.out.println("한영키 감지");
		});
	}
}

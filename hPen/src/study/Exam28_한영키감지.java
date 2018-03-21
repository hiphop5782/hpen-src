package study;

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Exam28_한영키감지 {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setBounds(100, 100, 500, 500);
		
		JTextArea field = new JTextArea();
		JScrollPane pane = new JScrollPane(field);
		field.setFont(new Font("", Font.PLAIN, 30));
		
		frame.getContentPane().add(pane);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		field.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				System.out.println("event");
			}
		});
		
		frame.setVisible(true);
	}
}

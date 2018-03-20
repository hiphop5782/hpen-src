package study;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

public class Exam05_글자조합 {
	public static void main(String[] args) throws Exception{
		//숫자 - 9
		//알파벳 - 2
		//특수문자 - 20번대
		//컨트롤알트쉬프트 - 0
		//백스페이스, 탭, 엔터 - 15
		
		
//		JFrame frame = new JFrame();
//		frame.setSize(400, 300);
//		frame.setLocationByPlatform(true);
//		frame.setAlwaysOnTop(true);
//		frame.setResizable(false);
//		frame.setVisible(true);
//		frame.setFocusTraversalKeysEnabled(false);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.addKeyListener(new KeyAdapter(){
//			@Override
//			public void keyPressed(KeyEvent e) {
//				System.out.println(Character.getType(e.getKeyChar()));
//			}
//		});
		
		int a = 'ㄱ';
		int b = 'ㅏ';
		int c = '가';
		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
		
		//[{(초성)×588}+{(중성)×28}+(종성)]+44032
		int uni = a * 588 + b * 28 + 0 + 44032;
		System.out.println(uni);
	}
}

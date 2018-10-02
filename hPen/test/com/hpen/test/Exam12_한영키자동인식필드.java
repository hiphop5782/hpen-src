package com.hpen.test;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.im.*;
import java.awt.event.*;
import javax.swing.event.*;

class Mal_Sub extends JFrame implements ActionListener, KeyListener, FocusListener {
	private Container con;
	private JTextField tf_korean, tf_english, tf_number;
	private JLabel lb_korean, lb_english, lb_number;
	private JPanel jp;

	private Dimension dimen, dimen1;
	private Toolkit tk;

	public Mal_Sub() {
		super("Test");
		this.init();
		this.gogo();

		this.setSize(300, 200);
		tk = Toolkit.getDefaultToolkit();
		dimen = tk.getScreenSize();
		dimen1 = getSize();
		this.setLocation((int) (dimen.getWidth() / 2 - dimen1.getWidth() / 2),
				(int) (dimen.getHeight() / 2 - dimen1.getHeight() / 2));
		this.setVisible(true);
	}

	public void init() {
		con = this.getContentPane();
		jp = new JPanel(new GridLayout(3, 2));

		lb_korean = new JLabel("          한글  입력  :  ");
		lb_english = new JLabel("          Input  English  :  ");
		lb_number = new JLabel("          Input  Number  :  ");

		tf_korean = new JTextField(20);
		tf_english = new JTextField(20);
		tf_number = new JTextField(20);

		// 다시 영문으로...
		// Character.Subset[] subset = null;
		// inCtx.setCharacterSubsets( subset );

		jp.add(lb_korean);
		jp.add(tf_korean);
		jp.add(lb_english);
		jp.add(tf_english);
		jp.add(lb_number);
		jp.add(tf_number);

		con.add(jp);
	}

	/*
	 * public void toKoreanIME(Component comp) { try { InputContext inCtx =
	 * comp.getInputContext(); Character.Subset[] subset = {
	 * Character.UnicodeBlock.HANGUL_SYLLABLES }; inCtx.setCharacterSubsets(
	 * subset ); }catch (Exception x) { } }
	 * 
	 */

	public void gogo() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tf_korean.addActionListener(this);
		tf_english.addActionListener(this);
		tf_korean.addKeyListener(this);
		tf_english.addKeyListener(this);
		tf_number.addKeyListener(this);

		tf_korean.addFocusListener(this);
		tf_english.addFocusListener(this);
		tf_number.addFocusListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == tf_korean) { // 한글 입력란에 한글을 입력한 후에 엔터를 치면.. 포커스를
											// 넘긴다(마치 탭키처럼)
			tf_english.requestFocus();
		} else if (e.getSource() == tf_english) {// 영어란에 영어을 입력한 후에 엔터를 치면..
													// 포커스를 넘긴다(마치 탭키처럼)
			tf_number.requestFocus();
		}
	}

	public void keyPressed(KeyEvent e) {
		// Invoked when a key has been pressed.
		if (e.getKeyCode() == e.VK_ESCAPE) {
			JOptionPane.showMessageDialog(null, "Esc  is  not  permitted.", "information",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void keyReleased(KeyEvent e) {
		// Invoked when a key has been released.

	}

	public void keyTyped(KeyEvent e) {
		// Invoked when a key
	}

	public void focusGained(FocusEvent e) {
		if (e.getSource() == tf_korean) {
			try {
				InputContext inCtx2 = tf_korean.getInputContext(); // comp는 text
																	// component
				Character.Subset[] subset2 = { Character.UnicodeBlock.HANGUL_SYLLABLES };
				inCtx2.setCharacterSubsets(subset2);
			} catch (Exception ee) {
			}
		} else if (e.getSource() == tf_english) {
			try {
				InputContext inCtx = tf_korean.getInputContext(); // comp는 text
																	// component
				Character.Subset[] subset = null;
				inCtx.setCharacterSubsets(subset);
			} catch (Exception ee) {
			}
		} else if (e.getSource() == tf_number) {
			try {
				InputContext inCtx = tf_korean.getInputContext(); // comp는 text
																	// component
				Character.Subset[] subset = { Character.UnicodeBlock.NUMBER_FORMS };
				inCtx.setCharacterSubsets(subset);
			} catch (Exception ee) {
			}
		}
	}

	public void focusLost(FocusEvent e) {

	}
}

public class Exam12_한영키자동인식필드 {
	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);

		new Mal_Sub();
	}
}

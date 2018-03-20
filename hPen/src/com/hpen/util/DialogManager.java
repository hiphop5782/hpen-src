package com.hpen.util;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class DialogManager {
	public static void alert(String msg) {
		JLabel label = new JLabel(msg);
		label.setFont(new Font("굴림", Font.PLAIN, 20));
		JOptionPane.showMessageDialog(null, label, "알림", JOptionPane.PLAIN_MESSAGE);
	}

	public static int confirm(String string) {
		JLabel label = new JLabel(string);
		label.setFont(new Font("굴림", Font.PLAIN, 20));
		return JOptionPane.showConfirmDialog(null, label, "최신 버전 다운로드", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	}
}

package com.hpen.property.ui;

import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import com.hpen.property.ProgramIcon;

public class MarkDownViewer extends JFrame{
	private JEditorPane editor = new JEditorPane();
	private JScrollPane scroll = new JScrollPane(editor);
	public MarkDownViewer(String text) throws FileNotFoundException {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationByPlatform(true);
		setIconImage(ProgramIcon.getIcon());
		setTitle(" hPen 사용설명서");
		setSize(900, 600);
		add(scroll);
		editor.setFont(new Font("굴림", Font.PLAIN, 25));
		editor.setText(text);
		editor.setEditable(false);
		this.setVisible(true);
	}
	
}






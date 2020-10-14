package com.hpen.property.ui;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import com.hpen.property.ProgramIcon;

public class MarkDownViewer extends JFrame{
	private JLabel label = new JLabel();
	private JScrollPane scroll = new JScrollPane(label);
	
	private String loadString(String filename) throws FileNotFoundException {
		Scanner s = new Scanner(new File(filename));
		StringBuffer buffer = new StringBuffer();
		while(s.hasNextLine()) {
			buffer.append(s.nextLine());
			buffer.append('\n');
		}
		return buffer.toString();
	}
	
	public MarkDownViewer(String text) throws FileNotFoundException {
		StyleSheet style = new StyleSheet();
		style.addRule(loadString("css/github.css"));
		HTMLEditorKit kit = new HTMLEditorKit();
		kit.setStyleSheet(style);
		
		Parser parser = Parser.builder().build();
		Node document = parser.parse(text);
		HtmlRenderer renderer = HtmlRenderer.builder().build();
		text = "<html>"+renderer.render(document)+"</html>";
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationByPlatform(true);
		setIconImage(ProgramIcon.getIcon());
		setTitle(" hPen 사용설명서");
		add(scroll);
		
		label.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		label.setOpaque(true);
		label.setBackground(Color.white);
		label.setText(text);
		scroll.getVerticalScrollBar().setUnitIncrement(20);
		setVisible(true);
		pack();
		setSize(getWidth() + 30, 500);
	}
	
}






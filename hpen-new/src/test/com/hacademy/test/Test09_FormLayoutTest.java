package com.hacademy.test;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test09_FormLayoutTest {
	static class TestFrame extends JFrame{
		TestFrame(){
			screen();
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setBounds(100, 100, 500, 500);
			setResizable(false);
			setVisible(true);
		}
		void screen() {
			CellConstraints cc = new CellConstraints();
			DefaultFormBuilder builder = new DefaultFormBuilder(new FormLayout(""));
			builder.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			builder.appendColumn("right:pref");
			builder.appendColumn("3dlu");;
			builder.appendColumn("pref");
			builder.appendColumn("5dlu");
			builder.appendColumn("right:pref");
			builder.appendColumn("3dlu");
			builder.appendColumn("pref");
			
			builder.append("First:", new JTextField(), 3);

	        builder.append("Last:", new JTextField());
	        builder.nextLine();

	        builder.append("Married:", new JCheckBox());
	        builder.nextLine();

	        builder.append("Phone:", new JTextField());
	        builder.nextLine();

	        builder.append("Fax:", new JTextField());
	        builder.nextLine();

	        builder.append("Email:", new JTextField());
	        builder.nextLine();

	        builder.appendSeparator("Work");

	        builder.append("Company:", new JTextField());
	        builder.nextLine();

	        builder.append("Phone:", new JTextField());
	        builder.nextLine();

	        builder.append("Fax:", new JTextField());
	        builder.nextLine();
			
			setContentPane(builder.getContainer());
		}
	}
	
	public static void main(String[] args) {
		TestFrame t = new TestFrame();
	}
}

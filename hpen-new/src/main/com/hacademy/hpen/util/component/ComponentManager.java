package com.hacademy.hpen.util.component;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.hacademy.hpen.util.loader.annotation.Component;

@Component
public class ComponentManager {
	/**
	 *	swing label factory
	 */
	public JLabel label(String text) {
		JLabel label = new JLabel(text, JLabel.CENTER);
		label.setOpaque(false);
		return label;
	}
	
	/**
	 *	swing checkbox factory 
	 */
	public JCheckBox checkbox() {
		return checkbox(false);
	}
	public JCheckBox checkbox(boolean check) {
		JCheckBox checkbox = new JCheckBox();
		checkbox.setSelected(check);
		return checkbox;
	}
	/**
	 *	swing radio button factory 
	 */
	public JRadioButton radio(ButtonGroup group, String text) {
		return radio(group, text, false);
	}
	public JRadioButton radio(ButtonGroup group, String text, boolean check) {
		JRadioButton radio = new JRadioButton(text);
		radio.setSelected(check);
		group.add(radio);
		return radio;
	}
	
	/**
	 *	swing textfield factory 
	 */
	public JTextField field() {
		return field("");
	}
	public JTextField field(String text) {
		JTextField field = new JTextField(text);
		return field;
	}
	
	/**
	 * 	swing button factory
	 */
	public JButton button(String text, ActionListener listener) {
		JButton button = new JButton(text);
		button.addActionListener(listener);
		return button;
	}
	public JButton button(Color color, ActionListener listener) {
		JButton button = new JButton();
		button.setBackground(color);
		button.addActionListener(listener);
		return button;
	}
	public JButton button(String text, Color color, ActionListener listener) {
		JButton button = button(text, listener);
		button.setBackground(color);
		return button;
	}
	
	/**
	 *	swing dropdown list factory 
	 */
	public <T>JComboBox<T> combo(T ... unit){
		JComboBox<T> combo = new JComboBox<T>();
		for(T t : unit) {
			combo.addItem(t);
		}
		return combo;
	}
	
	
}

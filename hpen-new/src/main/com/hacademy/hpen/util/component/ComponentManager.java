package com.hacademy.hpen.util.component;

import java.awt.Color;

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
	public JButton button(String text) {
		JButton button = new JButton(text);
		return button;
	}
	public JButton button(Color color) {
		JButton button = button("");
		button.setBackground(color);
		return button;
	}
	public JButton button(String text, Color color) {
		JButton button = button(text);
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
	
	/**
	 * 	get opposite color & contrast color
	 */
	public Color getContrastColor(Color color) {
		double y = (299 * color.getRed() + 587 * color.getGreen() + 114 * color.getBlue()) / 1000;
		return y >= 128 ? Color.black : Color.white;	
	}
	public Color getOppositeColor(Color color) {
		return new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue(), color.getAlpha());
	}
}

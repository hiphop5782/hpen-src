package com.hacademy.hpen.ui.option.process;

import java.awt.Component;
import java.util.HashMap;

import javax.swing.JCheckBox;
import javax.swing.JRadioButton;

public class ComponentMap extends HashMap<String, Component>{

	private static final long serialVersionUID = 1L;
	
	public <T>T get(String name, Class<T> clazz){
		Component c = get(name);
		return clazz.cast(c);
	}
	
	public boolean isSelected(String name) {
		Component c = get(name);
		if(c instanceof JCheckBox) {
			return ((JCheckBox)c).isSelected();
		}
		if(c instanceof JRadioButton) {
			return ((JRadioButton)c).isSelected();
		}
		throw new IllegalArgumentException("component is not a checkbox type");
	}
	
}	

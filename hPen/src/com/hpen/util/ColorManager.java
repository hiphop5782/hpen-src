package com.hpen.util;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.Format;

public class ColorManager {
	public static Color createColor(String hexString) throws IllegalArgumentException{
		if(hexString == null || hexString.length() != 6) throw new IllegalArgumentException();
		int r = Integer.parseInt(hexString.substring(0, 2), 16);
		int g = Integer.parseInt(hexString.substring(2, 4), 16);
		int b = Integer.parseInt(hexString.substring(4), 16);
		return new Color(r, g, b);
	}
	public static String getColorName(Color color) throws NullPointerException{
		if(color == null) throw new NullPointerException();
		String hexString = "";
		hexString += String.format("%02x", color.getRed());
		hexString += String.format("%02x", color.getGreen());
		hexString += String.format("%02x", color.getBlue());
		return hexString;
	}
}

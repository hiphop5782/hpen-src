package com.hacademy.hpen.ui.note.element;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Rect extends Node{
	
	public Rect(int x, int y, int width, int height, int thickness, Color color) {
		super.x = x;
		super.y = y;
		super.width = width;
		super.height = height;
		super.thickness = thickness;
		super.color = color;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(color);
		g.setStroke(new BasicStroke(thickness));
		g.drawRect(x, y, width, height);
	}

}

package com.hacademy.hpen.ui.note.element;

import java.awt.Graphics2D;

public class Rect extends Node{
	@Override
	public void draw(Graphics2D g) {
		g.drawRect(0, 0, width, height);
	}

}

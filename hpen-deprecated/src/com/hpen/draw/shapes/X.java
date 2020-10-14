package com.hpen.draw.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

public class X extends Rect{
	public X(Point s, Point e, int thick, Color color) {
		super(s, e, thick, color);
	}
	@Override
	public void draw(Graphics2D g2d) {
		if(this.isReady()){
			g2d.drawLine(getStartPositionX(), getStartPositionY(), 
					getStartPositionX()+getWidth(), getStartPositionY()+getHeight());
			g2d.drawLine(getStartPositionX()+getWidth(), getStartPositionY(), 
					getStartPositionX(), getStartPositionY()+getHeight());
		}
	}
}

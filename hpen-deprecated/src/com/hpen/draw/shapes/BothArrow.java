package com.hpen.draw.shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import com.hpen.util.MathManager;

public class BothArrow extends Arrow{
	private Point p3;
	private Point p4;

	public BothArrow(Point s, Point e, int thick, Color color) {
		super(s, e, thick, color);
	}
	
	@Override
	protected void setArrowPoint(int sx, int sy, int ex, int ey, int thick) {
		super.setArrowPoint(sx, sy, ex, ey, thick);
		int radius = thick * 3;
		int gap = 20;
		int line_angle = MathManager.getAngle(sx, sy, ex, ey);
		p3 = MathManager.getPoint(sx, sy, (line_angle+gap) % 360, radius);
		p4 = MathManager.getPoint(sx, sy, (line_angle-gap) % 360, radius);
	}

	@Override
	public void draw(Graphics2D g2d) {
		super.draw(g2d);
		g2d.drawLine(sx, sy, p3.x, p3.y);
		g2d.drawLine(sx, sy, p4.x, p4.y);
	}
	
	@Override
	public void move(int i, int j) {
		super.move(i, j);
		p3.x += i;		p3.y += j;
		p4.x += i;		p4.y += j;
	}
}

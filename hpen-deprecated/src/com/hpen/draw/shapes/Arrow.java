package com.hpen.draw.shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import com.hpen.util.MathManager;

public class Arrow extends Line{
	protected Point p1;
	protected Point p2;
	
	public Arrow(Point s, Point e, int thick, Color color){
		super(s, e, thick, color);
		setArrowPoint(s, e, thick);
	}
	public Arrow(int sx, int sy, int ex, int ey, int thick, Color color) {
		super(sx, sy, ex, ey, thick, color);
		setArrowPoint(sx, sy, ex, ey, thick);
	}
	
	protected void setArrowPoint(Point s, Point e, int thick){
		this.setArrowPoint(s.x, s.y, e.x, e.y, thick);
	}
	protected void setArrowPoint(int sx, int sy, int ex, int ey, int thick){
		int radius = thick * 3;
		int gap = 20;
		
		int line_angle = MathManager.getAngle(sx, sy, ex, ey);
		p1 = MathManager.getPoint(ex, ey, (180 + line_angle+gap) % 360, radius);
		p2 = MathManager.getPoint(ex, ey, (180 + line_angle-gap) % 360, radius);
	}

	@Override
	public void draw(Graphics2D g2d) {
		super.draw(g2d);
		g2d.drawLine(ex, ey, p1.x, p1.y);
		g2d.drawLine(ex, ey, p2.x, p2.y);
	}
	
	@Override
	public void move(int i, int j) {
		super.move(i, j);
		p1.x += i;		p1.y += j;
		p2.x += i;		p2.y += j;
	}

}

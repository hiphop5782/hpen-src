package com.hpen.draw.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import com.hpen.util.MathManager;

public class Thunder extends Line{
	
	protected Point upper;
	protected Point lower;
	
	public static final int MAXGAP = 15;

	public Thunder(Point s, Point e, int thick, Color color) {
		super(s, e, thick, color);
		int distance = Math.min(MathManager.getDistance(s, e) / 8, MAXGAP);
		upper = MathManager.getCrossPoint(s, e, distance, MathManager.UPPER);
		lower = MathManager.getCrossPoint(s, e, distance, MathManager.LOWER);
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		if(this.isReady()){
			g2d.drawLine(sx, sy, upper.x, upper.y);
			g2d.drawLine(upper.x, upper.y, lower.x, lower.y);
			g2d.drawLine(lower.x, lower.y, ex, ey);
		}
	}

	@Override
	public void move(int i, int j) {
		super.move(i, j);
		upper.x += i;		upper.y += j;
		lower.x += i;			lower.y += j;
	}
}

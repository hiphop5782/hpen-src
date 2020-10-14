package com.hpen.draw.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.QuadCurve2D;

public class CurvedLine extends Line{
	protected int mx = -1, my = -1;
	
//	protected final int increase = 50;
	
	public CurvedLine(Point s, Point e, int thick, Color color) {
		super(s, e, thick, color);
		this.setMxMy();
	}
	
	private void setMxMy(){
		this.mx = (sx + ex) / 2;
		this.my = (sy + ey) / 2;
		
		//커브 추가(상단으로)
		this.my -= (ex - sx) / 3;
		//커브 추가(하단으로)
		//this.my += (ex - sx) / 3;
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		QuadCurve2D quad = new QuadCurve2D.Float();
		quad.setCurve(sx, sy, mx, my, ex, ey);
		g2d.draw(quad);
	}
	
	@Override
	public boolean isSamePosition(Point p) {
		QuadCurve2D quad = new QuadCurve2D.Float();
		quad.setCurve(sx, sy, mx, my, ex, ey);
		return quad.contains(p);
	}
	
	@Override
	public void move(int i, int j) {
		super.move(i, j);
		this.setMxMy();
	}
}

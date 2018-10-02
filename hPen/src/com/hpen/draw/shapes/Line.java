package com.hpen.draw.shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import com.hpen.util.MathManager;

public class Line extends Shape{
	protected int ex = -1, ey = -1;
	
	public Line(Point s, Point e){
		this(s.x, s.y, e.x, e.y);
	}
	
	public Line(Point s, Point e, int thick, Color color){
		this(s.x, s.y, e.x, e.y, thick, color);
	}
	
	public Line(int sx, int sy, int ex, int ey){
		this.setSx(sx);
		this.setSy(sy);
		this.setEx(ex);
		this.setEy(ey);
	}
	
	public Line(int sx, int sy, int ex, int ey, int thick, Color color){
		super(sx, sy, thick, color);
		this.setEx(ex);
		this.setEy(ey);
	}
	
	
	
	public void setEx(int ex){
		if(ex < 0) return;
		this.ex = ex;
	}
	
	public void setEy(int ey){
		if(ey < 0) return;
		this.ey = ey;
	}
	
	
	public boolean isReady(){
		return sx >= 0 && sy >= 0 && ex >= 0 && ey >= 0;
	}
	
	public int getEy(){
		return this.ey;
	}
	
	public int getEx(){
		return this.ex;
	}

	@Override
	public String toString() {
		return "Line [sx=" + sx + ", sy=" + sy + ", ex=" + ex + ", ey=" + ey + "]";
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		if(this.isReady()){
			g2d.drawLine(sx, sy, ex, ey);
		}
	}

	@Override
	public boolean isSamePosition(Point p) {
		return MathManager.checkCrash(new Point(sx, sy), new Point(ex, ey), p, thick);
	}

	@Override
	public void move(int i, int j) {
		this.setSx(sx + i);
		this.setSy(sy + j);
		this.setEx(ex + i);
		this.setEy(ey + j);
	}

}

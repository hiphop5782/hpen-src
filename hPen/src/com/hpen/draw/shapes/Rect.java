package com.hpen.draw.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import com.hpen.util.MathManager;

public class Rect extends Shape{
	protected int ex = -1, ey = -1;
	
	public Rect(Point s, Point e){
		this(s.x, s.y, e.x, e.y);
	}
	
	public Rect(Point s, Point e, int thick, Color color){
		super(s.x, s.y, thick, color);
		this.setEx(e.x);
		this.setEy(e.y);
	}
	
	public Rect(int sx, int sy, int ex, int ey){
		super(sx, sy);
		this.setEx(ex);
		this.setEy(ey);
	}
	
	public Rect(int sx, int sy, int ex, int ey, int thick, Color color){
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
	
	public int getStartPositionX(){
		return Math.min(sx, ex);
	}
	
	public int getStartPositionY(){
		return Math.min(sy, ey);
	}
	
	public int getWidth(){
		return Math.abs(sx-ex);
	}
	
	public int getHeight(){
		return Math.abs(sy-ey);
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		if(this.isReady()){
			g2d.drawRect(getStartPositionX(), getStartPositionY(), getWidth(), getHeight());
		}
	}

	@Override
	public boolean isSamePosition(Point p) {
		return 
		MathManager.checkCrash(new Point(sx, sy), new Point(sx, ey), p, thick) 
		|| MathManager.checkCrash(new Point(sx, sy), new Point(ex, sy), p, thick)
		|| MathManager.checkCrash(new Point(sx, ey), new Point(ex, ey), p, thick)
		|| MathManager.checkCrash(new Point(ex, sy), new Point(ex, ey), p, thick);
	}

	@Override
	public void move(int i, int j) {
		this.setSx(sx+i);
		this.setSy(sy+j);
		this.setEx(ex+i);
		this.setEy(ey+j);
	}

}

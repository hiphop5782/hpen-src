package com.hpen.draw.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

public abstract class Shape {
	protected int sx = -1, sy = -1;
	public void setSx(int sx){
		if(sx < 0) return;
		this.sx = sx;
	}
	public void setSy(int sy){
		if(sy < 0) return;
		this.sy = sy;
	}
	public int getSx(){
		return this.sx;
	}
	public int getSy(){
		return this.sy;
	}
	
	protected boolean select;
	public void select() {
		select = true;
	}
	public void unselect() {
		select = false;
	}
	protected Color color;
	protected int thick;
	protected Shape(){
		this(-1, -1, 5, Color.black);
	}
	protected Shape(int sx, int sy) {
		this(sx, sy, 5, Color.black);
	}
	protected Shape(int sx, int sy, int thick, Color color){
		this.sx = sx;
		this.sy = sy;
		this.thick = thick;
		this.color = color;
	}
	public abstract void move(int i, int j);
	public abstract void draw(Graphics2D g2d);
	public abstract boolean isSamePosition(Point p);
	public void draw2(Graphics2D g2d) {
		//select일 경우 테두리를 추가
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		if(select) {
			g2d.setStroke(new BasicStroke(thick+5));
			if(color.equals(Color.BLACK)) {
				g2d.setColor(Color.blue);
			}else {
				g2d.setColor(Color.black);
			}
			draw(g2d);
		}
		g2d.setStroke(new BasicStroke(thick));
		g2d.setColor(color);
		draw(g2d);
	}
}

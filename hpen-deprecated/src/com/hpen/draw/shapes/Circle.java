package com.hpen.draw.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import com.hpen.util.MathManager;

public class Circle extends Shape{
	private int ex = -1, ey = -1;

	public Circle(Point s, Point e, int thick, Color color){
		super(s.x, s.y, thick, color);
		this.setEx(e.x);
		this.setEy(e.y);
	}
	public Circle(Point s, Point e){
		this(s.x, s.y, e.x, e.y);
	}
	public Circle(int x, int y, int radius){
		super(x-radius, y-radius);
		this.setEx(x + radius);
		this.setEy(y + radius);
	}
	public Circle(int sx, int sy, int ex, int ey){
		super(sx, sy);
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
			g2d.drawOval(getStartPositionX(), getStartPositionY(), getWidth(), getHeight());
		}
	}
	
	@Override
	public boolean isSamePosition(Point p) {
		//타원 공식 => ((x-cx)²/a²) + ((y-cy)²/b²) = 1
		//좌표계 상 하단이 +y 방향이므로 수식을 수정
		//수정 공식 => ((x-cx)²/a²) + ((y+cy)²/b²) = 1
		//(y+cy)² / b² = 1 - (x-cx)² / a²;
		//(y+cy)² = (1 - (x-cx)² / a²) * b²
		int cx = (sx + ex) / 2;
		int cy = (sy + ey) / 2;
		int a = getWidth() / 2;	//가로축
		int b = getHeight() / 2;	//세로축
//		System.out.println("a = "+a+", b = "+b);
		
		for(int x = -a; x <= a; x++) {
			for(int y = -b; y <= b; y++) {
				double result = Math.pow(x, 2) / Math.pow(a, 2) + Math.pow(y, 2) / Math.pow(b, 2);
				if(result >= 0.999 && result <= 1.001) {
					Point c = new Point(x+cx, y+cy);
					//System.out.println(c+ " / " + p);
					int distance = MathManager.getDistance(c, p);
					if(distance <= thick) {
						return true;
					}
				}
			}
		}
		return false;
	}
	@Override
	public void move(int i, int j) {
		this.setSx(sx+i);
		this.setSy(sy+j);
		this.setEx(ex+i);
		this.setEy(ey+j);
	}

}

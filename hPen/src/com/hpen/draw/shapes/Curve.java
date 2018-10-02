package com.hpen.draw.shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.hpen.util.MathManager;

public class Curve extends Shape{
	
	private List<Line> list = new ArrayList<>();
	
	public Curve(){}
	
	private int sx = -1, sy = -1;
	
	public void add(int x, int y, int thick, Color color){
		if(sx == -1 || sy == -1){
			sx = x;	
			sy = y;
		}else{
			Line line = new Line(sx, sy, x, y, thick, color);
			sx = x;
			sy = y;
			list.add(line);
		}
	}
	
	int index = 0;
	@Override
	public void draw(Graphics2D g2d) {
//		전체 다그리면 느림
//		for(Line line : list){
//			line.draw2(g2d);
//		}
		
//		못그린 부분만 그리도록 구현
//		System.out.println("index = "+index);
		for( ; index < list.size(); index++) {
			Line line = list.get(index);
			line.draw2(g2d);
		}
	}

	@Override
	public boolean isSamePosition(Point p) {
		for(Line line : list) {
			Point s = new Point(line.sx, line.sy);
			Point e = new Point(line.ex, line.ey);
			if(MathManager.checkCrash(s, e, p, thick)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void move(int i, int j) {
		for(Line line : list) {
			line.move(i, j);
		}
	}
}

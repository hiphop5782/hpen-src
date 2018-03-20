package com.hpen.draw.shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import com.hpen.util.MathManager;

public class ReverseArrow extends Arrow{
	public ReverseArrow(Point s, Point e, int thick, Color color){
		super(s, e, thick, color);
	}
	
	protected void setArrowPoint(Point s, Point e, int thick){
		this.setArrowPoint(s.x, s.y, e.x, e.y, thick);
	}
	/**
	 * @param sx
	 * @param sy
	 * @param ex
	 * @param ey
	 * @param thick
	 * 화살표 지점 계산<br>
	 * 화살표 크기는 두께의 2배
	 */
	protected void setArrowPoint(int sx, int sy, int ex, int ey, int thick){
		int radius = thick * 3;
		int gap = 20;
		
		int line_angle = MathManager.getAngle(sx, sy, ex, ey);
		p1 = MathManager.getPoint(sx, sy, (line_angle+gap) % 360, radius);
		p2 = MathManager.getPoint(sx, sy, (line_angle-gap) % 360, radius);
		int tx = super.sx;		super.sx = super.ex;		super.ex = tx;
		int ty = super.sy;		super.sy = super.ey;		super.ey = ty;
	}

	@Override
	public void draw(Graphics2D g2d) {
		super.draw(g2d);
	}
}

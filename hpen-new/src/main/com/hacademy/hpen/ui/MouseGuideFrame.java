package com.hacademy.hpen.ui;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class MouseGuideFrame extends MultiOptionFrame{
	/**
	 * Mouse information class
	 */
	@Data
	private static class MouseStatus implements MouseMotionListener, MouseListener{
		private int x, y, dx, dy;
		private boolean press;
		public void clearDrag() { 
			dx = dy = -1; 
		}
		public boolean isDrag() {
			return press && dx >= 0 && dy >= 0;
		}
		public int getWidth() {
			return Math.abs(x-dx);
		}
		public int getHeight() {
			return Math.abs(y-dy);
		}
		@Override
		public void mouseMoved(MouseEvent e) {
			moveProcess(e);
		}
		@Override
		public void mouseDragged(MouseEvent e) {
			switch(e.getModifiersEx()) {
			case MouseEvent.BUTTON1_DOWN_MASK:
				dx = e.getX(); dy = e.getY(); break;
			default:
				moveProcess(e);	break;
			}
		}
		private void moveProcess(MouseEvent e) {
			System.out.println("move");
			x = e.getX();
			y = e.getY();
			clearDrag();
		}
		@Override
		public void mouseClicked(MouseEvent e) {}
		@Override
		public void mousePressed(MouseEvent e) {
			press = true;
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			press = false;
		}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
		public int getLeft() {
			return Math.min(x, dx);
		}
		public int getRight() {
			return Math.max(x, dx);
		}
		public int getTop() {
			return Math.min(y, dy);
		}
		public int getBottom() {
			return Math.max(y, dy);
		}
	}
	private final MouseStatus status = new MouseStatus();
	
	/**
	 * Guide color 설정
	 */
	private Color mouseGuideColor = Color.black;
	private Color tempShapeBorderColor = Color.blue;
	private Color tempShapeAreaColor = new Color(0,0,0,0);
	@Getter
	private Stroke stroke = new BasicStroke(2f);
	
	public MouseGuideFrame() {
		this(CAPTURE_MODE);
	}
	public MouseGuideFrame(int frameMode) {
		super(frameMode);
		addMouseMotionListener(status);
		addMouseListener(status);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2d = (Graphics2D)g;
		
		//필수
		g2d.setComposite(AlphaComposite.Src);

		//stroke 설정
		g2d.setStroke(stroke);
		
		System.out.println(status);
		if(status.isDrag()) {
			//테두리 그리기
			g2d.setColor(tempShapeBorderColor);
			g2d.drawRect(status.getLeft(), status.getTop(), status.getWidth(), status.getHeight());
			
			//캡쳐영역 투명처리
			g2d.setColor(tempShapeAreaColor);
			g2d.fillRect(status.getLeft(), status.getTop(), status.getWidth(), status.getHeight());
		}
		else {
			g2d.setColor(mouseGuideColor);
			g2d.drawLine(status.x, 0, status.x, getHeight());
			g2d.drawLine(0, status.y, getWidth(), status.y);
		}
	}
	
}


package com.hacademy.hpen.ui.event;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import com.hacademy.hpen.util.loader.annotation.Component;

import lombok.Data;

/**
 * Mouse information class
 */
@Data
@Component
public class MouseStatus implements MouseMotionListener, MouseListener, MouseWheelListener{
	private int x, y, dx, dy;
	private boolean press;
	private MouseEventListener listener;
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
		if(listener != null)
			listener.whenMouseMove(e);
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		switch(e.getModifiersEx()) {
		case MouseEvent.BUTTON1_DOWN_MASK:
			dx = e.getX(); dy = e.getY(); break;
		default:
			moveProcess(e);	break;
		}
		if(listener != null)
			listener.whenMouseDragged(e);
	}
	private void moveProcess(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		clearDrag();
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if(listener != null)
			listener.whenMouseClick(e);
	}
	@Override
	public void mousePressed(MouseEvent e) {
		press = true;
		if(listener != null)
			listener.whenMousePress(e);
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		if(listener != null)
			listener.whenMouseRelease(e);
		press = false;
		moveProcess(e);
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		if(listener != null)
			listener.whenMouseEnter(e);
	}
	@Override
	public void mouseExited(MouseEvent e) {
		if(listener != null)
			listener.whenMouseLeave(e);
	}
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
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(listener != null)
			listener.whenMouseWheel(e);
	}
	public Rectangle getRect() {
		return new Rectangle(getLeft(), getTop(), getWidth(), getHeight());
	}
}
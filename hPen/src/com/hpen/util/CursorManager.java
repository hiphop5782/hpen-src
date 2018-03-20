package com.hpen.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import com.hpen.draw.shapes.Icon;
import com.hpen.property.DrawingOption;

public class CursorManager {
	public static final int CURSOR_MAXIMUM_SIZE = 20;
	public static final int CURSOR_MINIMUM_SIZE = 3;
	public static final int CURSOR_BASE_SIZE = 20;
	
	public static final int TEXT_MAXIMUM_SIZE = 20;
	public static final int TEXT_MINIMUM_SIZE = 3;
	public static final int TEXT_BASE_SIZE = 20;
	
	public static final int ICON_MAXIMUM_SIZE = 99;
	public static final int ICON_MINIMUM_SIZE = 3;
	public static final int ICON_BASE_SIZE = 40;
	
	public static Cursor createEmptyCursor(){
		try{
			BufferedImage base = new BufferedImage(CURSOR_BASE_SIZE, CURSOR_BASE_SIZE, BufferedImage.TYPE_INT_ARGB);
			Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(base, new Point(0, 0), "pen");
			return cursor;
		}catch(Exception e){
			return null;
		}
	}
	public static Cursor createCircleCursor(){
		return createCircleCursor(DrawingOption.getInstance().getPointColor(), DrawingOption.getInstance().getPointThickness());
	}
	public static Cursor createCircleCursor(int size){
		return createCircleCursor(Color.black, size);
	}
	public static Cursor createCircleCursor(Color color, int size){
		if(size > CURSOR_MAXIMUM_SIZE || size < CURSOR_MINIMUM_SIZE) 
			return null;
		try{
			BufferedImage base = new BufferedImage(CURSOR_BASE_SIZE, CURSOR_BASE_SIZE, BufferedImage.TYPE_INT_ARGB);
			Graphics pen = base.getGraphics();
			pen.setColor(color);
			pen.fillOval(0, 0, size, size);
			Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(base, new Point(size/2, size/2), "pen");
			return cursor;
		}catch(Exception e){
			return null;
		}
	}
	public static Cursor increaseCircleCursor(){
		if(DrawingOption.getInstance().getPointThickness() > CURSOR_MAXIMUM_SIZE
				|| DrawingOption.getInstance().getPointThickness() < CURSOR_MINIMUM_SIZE)
			return null;
		return createCircleCursor(DrawingOption.getInstance().getPointColor(), DrawingOption.getInstance().getPointThickness());
	}
	
	public static Cursor decreaseCircleCursor(){
		if(DrawingOption.getInstance().getPointThickness() > CURSOR_MAXIMUM_SIZE
				|| DrawingOption.getInstance().getPointThickness() < CURSOR_MINIMUM_SIZE)
			return null;
		return createCircleCursor(DrawingOption.getInstance().getPointColor(), DrawingOption.getInstance().getPointThickness());
	}
	
	public static Cursor createTextCursor(){
		Cursor cursor = createTextCursor(DrawingOption.getInstance().getPointColor(), DrawingOption.getInstance().getFontSize());
		return cursor;
	}
	
	private static Font textSizeFont = new Font("굴림", Font.PLAIN, 10);
	public static Cursor createTextCursor(Color color, int size){
		try{
			BufferedImage base = new BufferedImage(TEXT_BASE_SIZE, TEXT_BASE_SIZE, BufferedImage.TYPE_INT_ARGB);
			Graphics pen = base.getGraphics();
			Graphics2D pen2d = (Graphics2D)pen;
			pen2d.setStroke(new BasicStroke(2));
			pen2d.setColor(color);
			
//			테스트용 사각형
//			pen2d.drawRect(0, 0, TEXT_BASE_SIZE, TEXT_BASE_SIZE);
			
			pen2d.drawLine(0, 0, 0, TEXT_MAXIMUM_SIZE);
			pen2d.setFont(textSizeFont);
			pen2d.drawString(String.valueOf(size), 3, 12);
			Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(base, new Point(0, 0), "pen");
			return cursor;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static Cursor increaseTextCursor(){
		if(DrawingOption.getInstance().getPointThickness() > TEXT_MAXIMUM_SIZE
				|| DrawingOption.getInstance().getPointThickness() < TEXT_MINIMUM_SIZE)
			return null;
		return createTextCursor(DrawingOption.getInstance().getPointColor(), DrawingOption.getInstance().getFontSize());
	}
	
	public static Cursor decreaseTextCursor(){
		if(DrawingOption.getInstance().getPointThickness() > TEXT_MAXIMUM_SIZE
				|| DrawingOption.getInstance().getPointThickness() < TEXT_MINIMUM_SIZE)
			return null;
		return createTextCursor(DrawingOption.getInstance().getPointColor(), DrawingOption.getInstance().getFontSize());
	}
	
	private static Font iconSizeFont = new Font("굴림", Font.PLAIN, 14);
	public static Cursor createIconCursor(Icon icon) {
		try{
			BufferedImage base = new BufferedImage(ICON_BASE_SIZE, ICON_BASE_SIZE, BufferedImage.TYPE_INT_ARGB);
			Graphics pen = base.getGraphics();
			Graphics2D pen2d = (Graphics2D)pen;
			pen2d.setStroke(new BasicStroke(2));
			pen2d.setColor(Color.black);
			
			//크기 확인용 사각형
			//pen2d.drawRect(0, 0, ICON_BASE_SIZE, ICON_BASE_SIZE);
			
			//왼쪽 모서리
			//pen2d.drawPolygon(new int[] {0,2,0}, new int[] {0,0,2}, 3);
			
			pen2d.drawImage(icon.getImageIcon().getImage(), 2, 2, ICON_BASE_SIZE*2/3, ICON_BASE_SIZE*2/3, null);
			pen2d.setFont(iconSizeFont);
			pen2d.drawString(String.valueOf(DrawingOption.getInstance().getIconSize()), 22, 36);
			Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(base, new Point(0, 0), "icon");
			return cursor;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static Cursor increaseIconCursor(Icon icon){
		if(DrawingOption.getInstance().getPointThickness() > ICON_MAXIMUM_SIZE
				|| DrawingOption.getInstance().getPointThickness() < ICON_MINIMUM_SIZE)
			return null;
		return createIconCursor(icon);
	}
	
	public static Cursor decreaseIconCursor(Icon icon){
		if(DrawingOption.getInstance().getPointThickness() > ICON_MAXIMUM_SIZE
				|| DrawingOption.getInstance().getPointThickness() < ICON_MINIMUM_SIZE)
			return null;
		return createIconCursor(icon);
	}
	
	private static Cursor grab = new Cursor(Cursor.HAND_CURSOR);
	public static Cursor createGrabCursor() {
		return grab;
	}
}














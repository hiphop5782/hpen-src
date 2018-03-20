package com.hpen.util;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.SwingUtilities;

import com.hpen.draw.shapes.Shape;
import com.hpen.property.DrawingOption;

/**
 * @author Administrator
 * <h1>그림 그릴 때 필요한 데이터<h1>
 */
public class ScreenData{
	
	/**
	 * 현재 커서의 정보
	 */
	private Point cursor = new Point(-1, -1);
	public void setCursor(Point p){
		setCursor(p.x, p.y);
	}
	public Point getCursor() {
		return cursor;
	}
	public void setCursor(int x, int y){
		cursor.x = x;		cursor.y = y;
	}
	public Point getStartAtFrame(Window window){
		Point np = new Point(start);
		SwingUtilities.convertPointFromScreen(np, window);
		return np;
	}
	public Point getEndAtFrame(Window window){
		Point np = new Point(end);
		SwingUtilities.convertPointFromScreen(np, window);
		return np;
	}
	public Point getCursorAtFrame(Window window){
		Point np = new Point(cursor);
		SwingUtilities.convertPointFromScreen(np, window);
		return np;
	}
	public int getCursorDrawingX(){
		return cursor.x - DrawingOption.getInstance().getPointThickness();
	}
	public int getCursorDrawingY(){
		return cursor.y - DrawingOption.getInstance().getPointThickness();
	}
	public int getDiameter(){
		return DrawingOption.getInstance().getPointThickness()*2;
	}
	
	public boolean isCursorDrawable(){
		return cursor.x >= 0 && cursor.y >= 0;
	}
	
	/**
	 * 도형 그리기의 시작점 정보
	 */
	private Point start = new Point(-1, -1);
	public void setStart(Point p){
		setStart(p.x, p.y);
	}
	public void setStart(int x, int y){
		start.x = x;			start.y = y;
	}
	
	/**
	 * 도형 그리기의 끝점 정보
	 */
	private Point end = new Point(-1, -1);
	public void setEnd(Point p){
		setEnd(p.x, p.y);
	}
	public void setEnd(int x, int y){
		end.x = x;			end.y = y;
	}
	
	public Point getEnd(){
		return end;
	}
	
	public Point getStart(){
		return start;
	}
	
	public int getStartX(){
		return Math.min(start.x, end.x);
	}
	
	public int getStartY(){
		return Math.min(start.y, end.y);
	}
	
	public int getWidth(){
		return cursor.x - start.x;
	}
	
	public int getHeight(){
		return cursor.y - start.y;
	}
	
	public Rectangle getRectangle(){
		return new Rectangle(getStartX(), getStartY(), getWidth(), getHeight());
	}
	
	public boolean isTempShapeDrawable() {
		return	start.x + start.y >= 0 && end.x + end.y >= 0;
	}
	
	/**
	 * 도형을 저장하는 ArrayList
	 */
	private ArrayList<Shape> shapelist = new ArrayList<>();
	private Stack<Shape> redolist = new Stack<>();
	
	@Override
	public String toString() {
		String str = "";
		str += "cursor="+cursor+",\t";
		str += "start="+start+",\t";
		str += "end="+end+",\t";
		//str += "";
		//str += "";
		return str;
	}

	public void addShape(Shape shape) {
		backup();
		this.shapelist.add(shape);
	}

	public ArrayList<Shape> getShapelist() {
		return this.shapelist;
	}

	public boolean isShapeEmpty() {
		return this.shapelist == null || this.shapelist.size() == 0;
	}
	
	public void clearCursor(){
		cursor.x = -1;		cursor.y = -1;
	}
	public void clearStart(){
		start.x = -1;		start.y = -1;
	}
	public void clearEnd(){
		end.x = -1;			end.y = -1;
	}
	public void clear(){
		clearStart();
		clearEnd();
		clearShape();
	}

	public void undo() {
		backup();
		Shape shape = shapelist.remove(shapelist.size()-1);
		redolist.push(shape);
	}
	
	public void redo() {
		if(redolist.size() > 0) {
			shapelist.add(redolist.pop());
		}
	}

	private static List<Shape> backup = new ArrayList<>();
	public void clearShape() {
		backup.clear();
		backup.addAll(shapelist);
		shapelist.clear();
	}
	
	public void backup() {
		backup.clear();
		backup.addAll(shapelist);
	}

	public void recovery() {
		shapelist.clear();
		shapelist.addAll(backup);
	}
	
	public List<Shape> findShape(int x, int y) {
		List<Shape> list = new ArrayList<>();
		for(Shape shape : shapelist) {
			if(shape.isSamePosition(new Point(x, y))) {
				shape.select();
				list.add(shape);
			}else {
				shape.unselect();
			}
		}
		return list;
	}
	public void clearChoice() {
		for(Shape s : shapelist) {
			s.unselect();
		}
	}
}
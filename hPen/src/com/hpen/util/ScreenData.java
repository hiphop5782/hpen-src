package com.hpen.util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

import com.hpen.draw.shapes.Shape;
import com.hpen.property.DrawingOption;
import com.hpen.util.image.ImageManager;

/**
 * @author Administrator
 * <h1>그림 그릴 때 필요한 데이터<h1>
 */
public class ScreenData{
	
	private ImageAndPath now;
	private ImageAndPath backup;
	
	public void createNowImage(int width, int height) {
		createNowImage(width, height, null);
	}
	public void createNowImage(int width, int height, BufferedImage background) {
		now = new ImageAndPath(width, height);
		now.setBackground(background);
//		System.out.println(now.getImage().getWidth()+", "+now.getImage().getHeight());
	}
	
	public BufferedImage getNowImage() {
	    return now.getImage();
	}
	
	public BufferedImage getNowImageCopy() {
	    return now.getImageCopy();
	}
	
	public boolean hasNowImage() {
		return now != null && now.hasImage();
	}
	
	public void drawShape(Shape shape) {
		now.addShape(shape);
		try {backup = now.clone();} catch (CloneNotSupportedException e) {}
	}
	
	public void recoverLastScreen() {
		try {now = backup.clone();}catch(CloneNotSupportedException e) {}
	}
	
	/**
	 * now + undo 이미지를 저장할 Collection
	 */
	private LinkedList<ImageAndPath> history = new LinkedList<>();
	public void addHistory(ImageAndPath inp) {
		history.addLast(inp);
//		System.out.println("history add = "+history.size());
	}
	public ImageAndPath removeHistory() {
		ImageAndPath last = history.pollLast();
//		System.out.println("history remove = "+history.size());
		return last;
	}
	public void clearHistory() {
		history.clear();
	}
	
	public boolean hasHistory() {
		return !history.isEmpty();
	}
	
	/**
	 * redo 이미지를 저장할 Collection 
	 */
	private LinkedList<ImageAndPath> future = new LinkedList<>();
	public void addFuture(ImageAndPath inp) {
		future.addFirst(inp);
//		System.out.println("future add = "+future.size());
	}
	public ImageAndPath removeFuture() {
		ImageAndPath first = future.pollFirst();
//		System.out.println("future remove = "+future.size());
		return first;
	}
	public void clearFuture() {
		future.clear();
	}
	public boolean hasFuture() {
		return !future.isEmpty();
	}
	
	/**
	 * 임시 저장 메모리(alt+num0 ~ alt+num9)
	 */
	private ImageAndPath[] memory = new ImageAndPath[10];
	public void addMemory(int index) {
		try {
			memory[index] = now.clone();
		}catch(Exception e) {}
	}
	public ImageAndPath getMemory(int index) {
		return memory[index];
	}
	public void loadMemory(int index) {
		try {
			now = memory[index].clone();
		}catch(Exception e) {}
	}
	
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
//		System.out.println("start = "+start+", end = "+end);
		return	start.x + start.y >= 0 && end.x + end.y >= 0;
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
//		buffer.append("cursor = "+cursor+", \t");
//		buffer.append("start="+start+",\t");
//		buffer.append("end="+end+",\t");
		buffer.append("history = "+history.size()+", future = "+future.size());
		buffer.append("\n");
		return buffer.toString();
	}

	public void addShape(Shape shape) {
		clearFuture();
		try {
			addHistory(now.clone());
		}catch(Exception e) {}
		drawShape(shape);
//		System.out.println("addShape = "+this);
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
		clearHistory();
		clearFuture();
		now.clear();
//		System.out.println("clear = "+this);
	}

	public void undo() {
		if(hasHistory()) {
			addFuture(now);
			now = removeHistory();
		}
//		System.out.println("undo = "+this);
	}
	
	public void redo() {
		if(hasFuture()) {
			addHistory(now);
			now = removeFuture();
		}
//		System.out.println("redo = "+this);
	}
	
	public List<Shape> findShape(int x, int y) {
		List<Shape> list = new ArrayList<>();
		for(Shape shape : now.getShapeList()) {
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
		for(Shape s : now.getShapeList()) {
			s.unselect();
		}
	}
	
}



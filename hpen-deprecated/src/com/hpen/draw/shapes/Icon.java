package com.hpen.draw.shapes;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import com.hpen.util.image.ImageManager;

public class Icon extends Shape{
	private ImageIcon icon;
	private int width, height;
	
	private int usage;
	public int getUsage(){
		return usage;
	}
	public void setUsage(){
		usage++;
	}
	public Icon(ImageIcon icon, int sx, int sy, int width, int height){
		this.icon =icon;
		this.setSx(sx);
		this.setSy(sy);
		this.setWidth(width);
		this.setHeight(height); 
	}
	public Icon(Icon icon){
		this(icon.icon, icon.sx, icon.sy, icon.width, icon.height);
	}
	public Icon(ImageIcon icon, int sx, int sy){
		this(icon, sx, sy, icon.getIconWidth(), icon.getIconHeight());
	}
	public Icon(ImageIcon icon){
		this(icon, 0, 0);
	}
	public Icon(Image image){
		this(image, image.getWidth(null), image.getHeight(null));
	}
	public Icon(Image image, int size){
		this(image, size, size);
	}
	public Icon(Image image, int width, int height){
		this(new ImageIcon(image), width, height);
	}
	
	public void resize(int width, int height){
		this.icon = new ImageIcon(ImageManager.resizeImage((BufferedImage)icon.getImage(), width, height));
		this.setWidth(width);
		this.setHeight(height);
	}
	
	public ImageIcon getImageIcon(){
		return icon;
	}
	public ImageIcon getIcon() {
		return icon;
	}
	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		if(width <= 0) return;
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		if(height <= 0) return;
		this.height = height;
	}
	public void setSize(int size) {
		setWidth(size);
		setHeight(size);
	}
	@Override
	public void draw(Graphics2D g2d) {
		if(icon != null)
			g2d.drawImage(icon.getImage(), sx, sy, width, height, null);
	}
	public Icon copy(){
		return copy(width, height);
	}
	public Icon copy(int size){
		return copy(size, size);
	}
	public Icon copy(int width, int height){
		Icon icon = new Icon(this);
		icon.resize(width, height);
		return icon;
	}
	@Override
	public String toString() {
		return "Icon [sx=" + sx + ", sy=" + sy + ", width=" + width + ", height=" + height + "]";
	}
	@Override
	public boolean isSamePosition(Point p) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void move(int i, int j) {
		this.setSx(sx + i);
		this.setSy(sy + j);
	}
}

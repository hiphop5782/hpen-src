package com.hpen.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import com.hpen.draw.shapes.Shape;
import com.hpen.draw.shapes.Text;
import com.hpen.util.image.ImageManager;

public class ImageAndPath {
	private BufferedImage image;
	
	public ImageAndPath(int width, int height) {
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	}
	
	public void resize(int width, int height) {
		image = ImageManager.resizeImage(image, width, height);
	}
	public BufferedImage getImage() {
		return image;
	}
	public BufferedImage getImageCopy() {
		return ImageManager.copyImage(image);
	}
	public boolean hasImage() {
		return image != null;
	}
	public void clear() {
		shapeList.clear();
		redrawImage();
	}
	
	private BufferedImage background;
	public void setBackground(BufferedImage background) {
		this.background = background;
		if(background == null) {
			image.getGraphics().setColor(Color.white);
			image.getGraphics().fillRect(0, 0, image.getWidth(), image.getHeight());
		}else {
			image.getGraphics().drawImage(background, 0, 0, image.getWidth(), image.getHeight(), null);
		}
	}
	
	public void redrawImage() {
		Graphics2D g2d = (Graphics2D) image.getGraphics();
		g2d.clearRect(0, 0, image.getWidth(), image.getHeight());
		if(background != null)
			g2d.drawImage(background, 0, 0, image.getWidth(), image.getHeight(), null);
		for(Shape shape : shapeList) {
			shape.draw2(g2d);
		}
	}
	
	private LinkedList<Shape> shapeList = new LinkedList<>();
	public void addShape(Shape shape) {
//		if(shape instanceof Text) {
//			Text text = (Text)shape;
//			System.out.println(text.getText());
//		}
		shape.draw2((Graphics2D) image.getGraphics());
		shapeList.addLast(shape);
	}
	public Shape removeShape() {
		Shape shape = shapeList.poll();
		redrawImage();
		return shape;
	}
	public int getShapeCount() {
		return shapeList.size();
	}
	public boolean hasShape() {
		return !shapeList.isEmpty();
	}
	
	@Override
	public ImageAndPath clone() throws CloneNotSupportedException {
		ImageAndPath copy = new ImageAndPath(image.getWidth(), image.getHeight());
		copy.image = getImageCopy();
		copy.background = ImageManager.copyImage(background);
		copy.shapeList = (LinkedList<Shape>) shapeList.clone();
		return copy;
	}

	public LinkedList<Shape> getShapeList() {
		return shapeList;
	}
}

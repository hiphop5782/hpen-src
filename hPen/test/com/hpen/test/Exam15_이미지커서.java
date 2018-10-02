package com.hpen.test;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Exam15_이미지커서 {
	public static void main(String[] args) throws IOException{
		JFrame frame = new JFrame();
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		BufferedImage buf = ImageIO.read(new File("image/cursor/base.png"));
		int size = 8;
		BufferedImage newBuf = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
		Graphics pen = newBuf.getGraphics();
		pen.setColor(Color.black);
		pen.fillOval((20-size)/2, (20-size)/2, size, size);
		pen.drawImage(buf, 0, 0, size, size, null);
		Image image = newBuf.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(image, new Point(10,10), "point");
		frame.setCursor(cursor);
	}
}

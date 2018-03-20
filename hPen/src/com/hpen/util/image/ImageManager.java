package com.hpen.util.image;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;

public class ImageManager {
	public static BufferedImage resizeImage(BufferedImage image, int width, int height){
		try{
			return Thumbnails.of(image).size(width, height).asBufferedImage();
		}catch(Exception e) {
			return null;
		}
	}
	
	/**
	 * 
	 * @param image		스프라이트 이미지
	 * @param row			이미지의 줄 수
	 * @param col			이미지의 칸 수
	 * @return	서브 이미지 배열
	 */
	public static BufferedImage[][] loadSpriteImage(BufferedImage image, int row, int col){
		int width = image.getWidth() / col;
		int height = image.getHeight() / row;
		
		BufferedImage[][] list = new BufferedImage[row][col];
		
		for(int i=0; i<row; i++){
			for(int j=0; j<col; j++){
				list[i][j] = image.getSubimage(width*j, height*i, width, height);
			}
		}
		
		return list;
	}
	
	public static void saveSpriteImage(BufferedImage image, int row, int col, File folder, String format){
		BufferedImage[][] list = loadSpriteImage(image, row, col);
		for(int i=0; i<row; i++){
			for(int j=0; j<col; j++){
				File target = new File(folder, "seperate"+i+j+"."+format);
				try{
					ImageIO.write(list[i][j], format, target);
				}catch(Exception e){
					System.err.println("이미지 저장 실패 : "+target);
				}
			}
		}
	}
	
	public static void saveSpriteImageAsPNG(BufferedImage image, int row, int col, File folder){
		saveSpriteImage(image, row, col, folder, "png");
	}
	
	public static void saveSpriteImageAsJPG(BufferedImage image, int row, int col, File folder){
		saveSpriteImage(image, row, col, folder, "jpg");
	}
	
	public static void saveSpriteImageAsGIF(BufferedImage image, int row, int col, File folder){
		saveSpriteImage(image, row, col, folder, "gif");
	}
}

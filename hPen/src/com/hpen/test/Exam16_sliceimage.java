package com.hpen.test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.hpen.util.image.ImageManager;

public class Exam16_sliceimage {
	public static void main(String[] args) throws IOException{
		BufferedImage image = ImageIO.read(new File("image/icon/emoticon.png"));
		ImageManager.saveSpriteImage(image, 8, 12, new File("image/temp"), "png");
	}
}

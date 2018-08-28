package com.hpen.test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.hpen.util.image.ImageManager;

import net.coobird.thumbnailator.Thumbnails;

public class Exam18_resizeImage {
	public static void main(String[] args) throws IOException {
//		File target = new File("D:\\SW-1\\4.JS\\workspace\\2_event\\img\\hol.jpg");
		File target = new File("J:\\1.lecture\\21.jQuery\\수업자료\\hwang\\수업\\7일\\img\\hol.jpg");
		BufferedImage image = ImageIO.read(target);
		BufferedImage newImage = Thumbnails.of(image).size(50, 50).asBufferedImage();
		ImageIO.write(newImage, "jpg", new File("temp.jpg"));
	}
}

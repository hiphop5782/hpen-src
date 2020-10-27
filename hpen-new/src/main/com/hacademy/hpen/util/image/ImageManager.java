package com.hacademy.hpen.util.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.hacademy.hpen.util.loader.annotation.Component;
import com.hacademy.hpen.util.screen.ImageType;

@Component
public class ImageManager {
	
	public void saveImageAsFile(BufferedImage image, File file, ImageType hint) throws IOException {
		ImageIO.write(image, hint.getValue(), file);
	}
	
	public void saveImageAsPngFile(BufferedImage image, File file) throws IOException {
		saveImageAsFile(image, file, ImageType.PNG);
	}
	
	public void saveImageAsJpgFile(BufferedImage image, File file) throws IOException {
		saveImageAsFile(image, file, ImageType.JPG);
	}
	
	
	
}

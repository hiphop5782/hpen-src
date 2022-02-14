package com.hacademy.hpen.util.image;

import java.awt.Window;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.Format;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.hacademy.hpen.util.loader.annotation.Component;
import com.hacademy.hpen.util.screen.ImageType;

@Component
public class ImageManager {
	
	JFileChooser chooser = new JFileChooser ();
	Format df = new DecimalFormat("000000");
	
	public void init() {
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.addChoosableFileFilter(new FileNameExtensionFilter("PNG 파일", "png"));
		chooser.addChoosableFileFilter(new FileNameExtensionFilter("JPG 파일", "jpg"));
	}
	
	public void saveImageAsFile(BufferedImage image, File file, ImageType hint) throws IOException {
		ImageIO.write(image, hint.getValue(), file);
	}
	
	public void saveImageAsPngFile(BufferedImage image, File file) throws IOException {
		saveImageAsFile(image, file, ImageType.PNG);
	}
	
	public void saveImageAsJpgFile(BufferedImage image, File file) throws IOException {
		saveImageAsFile(image, file, ImageType.JPG);
	}
	
	public void saveImageAsTempFile(BufferedImage image, ImageType hint) throws IOException {
		File baseDir = new File(System.getProperty("user.dir"), "temp");
		baseDir.mkdirs();
		File[] files = baseDir.listFiles();
		int count = files == null? 0 : files.length;
		File target = new File(baseDir, "capture"+df.format(count+1)+"."+hint.getValue());
		saveImageAsFile(image, target, hint);
	}
	
	public void saveImageAsPngTempFile(BufferedImage image) throws IOException {
		saveImageAsTempFile(image, ImageType.PNG);
	}
	
	public void saveImageAsJpgTempFile(BufferedImage image) throws IOException {
		saveImageAsTempFile(image, ImageType.JPG);
	}
	
	public void saveImageAsWithDialog(BufferedImage image, Window root) throws IOException {
		int choice = chooser.showSaveDialog(root);
		if(choice == JFileChooser.APPROVE_OPTION) {
			File chooseFile = chooser.getSelectedFile();
			FileNameExtensionFilter filter = (FileNameExtensionFilter)chooser.getFileFilter();
			String extension = filter.getExtensions()[0];
			if(!chooseFile.getName().toLowerCase().endsWith("."+extension.toLowerCase())) {
				chooseFile = new File(chooseFile.getAbsolutePath()+"."+extension.toLowerCase());
			}
			saveImageAsFile(image, chooseFile, ImageType.valueOf(extension.toUpperCase()));
		}
	}
	
}

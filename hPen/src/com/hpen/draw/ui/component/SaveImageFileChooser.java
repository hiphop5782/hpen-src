package com.hpen.draw.ui.component;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileFilter;

import com.hpen.property.CaptureOption;
import com.hpen.property.DrawingOption;

import sun.swing.*;

public class SaveImageFileChooser extends JFileChooser{
	private String[][] fileFilter = new String[][]{
		/*{".jpg","JPG 파일"},*/{".png", "PNG 파일"},{".gif", "GIF 파일"}
	};
	public SaveImageFileChooser(File path){
		super(path);
		
		this.setAcceptAllFileFilterUsed(false);
		this.setDialogTitle("필기를 그림 파일로 저장");
		for(int i=0; i<fileFilter.length; i++){
			this.addChoosableFileFilter(new ChooserFileFilter(fileFilter[i][0], fileFilter[i][1]));
		}
		this.setFileFilter(new ChooserFileFilter(fileFilter[0][0], fileFilter[0][1]));
	}
	
	private void changePath(String path, Class<?> c){
		if(c.getName().equals("com.hpen.draw.ui.DrawingFrame"))
			DrawingOption.getInstance().setSaveFolder(new File(path));
		else if(c.getName().equals("com.hpen.capture.ui.CaptureFrame"))
			CaptureOption.getInstance().setSaveFolder(path);
	}
	
	public void saveImage(BufferedImage capture, Class<?> c){
		if(this.getFileFilter().getDescription().equals("모든 파일")){
			File file = this.getSelectedFile();
			saveScreen(capture, "jpg", file);
		}else{
			changePath(this.getSelectedFile().getPath(), c);
			String path = this.getSelectedFile().getPath();
			String suffix = this.getFileFilter().getDescription();
			suffix = suffix.substring(suffix.lastIndexOf("(")+1, suffix.lastIndexOf(")"));
			String type = suffix.substring(1);
			File file = new File(path + suffix);
			saveScreen(capture, type, file);
		}
	}
	
	public void saveScreen(BufferedImage capture, String type, File file){
		try {
			ImageIO.write(capture, type, file);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "저장 권한이 없습니다");
			e.printStackTrace();
		}
	}
	
	class ChooserFileFilter extends FileFilter{
		String type; 
	    String desc; 
	    public ChooserFileFilter(String type, String desc){ 
	        this.type = type; 
	        this.desc = desc; 
	    } 
	    public boolean accept(File f){ 
	        return f.getName().endsWith(type) || f.isDirectory(); 
	    }
	    public String getDescription(){ 
	        if(type == null || type.equals(""))
	            return desc;
	        else
	            return desc + " (" + type + ")"; 
	    }
	}
	public void updateUI(){
	      LookAndFeel old = UIManager.getLookAndFeel();
	      try {
	         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	      } 
	      catch (Throwable ex) {
	         old = null;
	      } 

	      super.updateUI();

	      if(old != null){
	         FilePane filePane = findFilePane(this);
	         filePane.setViewType(FilePane.VIEWTYPE_DETAILS);
	         filePane.setViewType(FilePane.VIEWTYPE_LIST);

	         Color background = UIManager.getColor("Label.background");
	         setBackground(background);
	         setOpaque(true);

	         try {
	            UIManager.setLookAndFeel(old);
	         } 
	         catch (UnsupportedLookAndFeelException ignored) {} // shouldn't get here
	      }
	   }

	   private static FilePane findFilePane(Container parent){
	      for(Component comp: parent.getComponents()){
	         if(FilePane.class.isInstance(comp)){
	            return (FilePane)comp;
	         }
	         if(comp instanceof Container){
	            Container cont = (Container)comp;
	            if(cont.getComponentCount() > 0){
	               FilePane found = findFilePane(cont);
	               if (found != null) {
	                  return found;
	               }
	            }
	         }
	      }

	      return null;
	   }
}

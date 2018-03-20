package com.hpen.property;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.hpen.Starter;

public class PropertyLoader {
	static {
		load();
	}
	
	public static void load() {
		load(getStream("resource/version.prop"));
		load(getStream("resource/shortcut.prop"));
		DrawingOption.getInstance().setOptions(
				load(getStream("resource/draw.prop")));
		DrawingOption.getInstance().initialize();
		CaptureOption.getInstance().setOptions(
				load(getStream("resource/capture.prop")));
		load(getStream("resource/zoom.prop"));
	}
	
	public static void save() {
		File dir = new File(System.getProperty("user.dir"));
		File draw = new File(dir, "draw.prop");
		File capture = new File(dir, "capture.prop");
//		File zoom = new File(dir, "zoom.prop");
//		File shortcut = new File(dir, "shortcut.prop");
		
		try(FileOutputStream out = new FileOutputStream(draw)){
			DrawingOption.getInstance().getOptions().store(out,"");		
		}catch(IOException e) {}
		
		try(FileOutputStream out = new FileOutputStream(capture)){
			CaptureOption.getInstance().getOptions().store(out,"");		
		}catch(IOException e) {}
		
	}
	
	public static boolean createPropertyFolder(){
		File folder = new File(System.getProperty("user.home")+File.separator+".hPen");
		if(!folder.exists()) {
			folder.mkdirs();
			return false;
		}
		else {
			return true;
		}
	}
	
	private static void remove(File file){
		if(file == null || !file.exists()) return;
		if(file.isDirectory()){ 
			File[] list = file.listFiles();
			if(list == null) return;
			for(File f : list){
				remove(f);
			}
		}
		file.delete();
	}
	
	public static InputStream getStream(String uri) {
		return Starter.class.getResourceAsStream(uri);
	}
	
	public static Properties load(InputStream in) {
		Properties prop = new Properties();
		try {
			prop.load(in);
		}catch(Exception e) {}
		return prop;
	}
}

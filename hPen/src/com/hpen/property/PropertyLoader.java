package com.hpen.property;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.hpen.Starter;
import com.hpen.value.Version;

public class PropertyLoader {
	static {
		load();
	}
	
	public static void load(){
		String propertyFolderName = System.getProperty("user.home");
		File propertyFolder = new File(propertyFolderName, ".hPen");
		try {
			if(propertyFolder.isDirectory()) { 
				loadUserProperty();
				return;
			}
		}catch(Exception e) {}
		loadDefaultProperty();
	}
	
	public static void loadUserProperty() throws FileNotFoundException {
		Version.getInstance().setValues(
				load(getPropertyStream("version.prop")));
		ShortcutOption.getInstance().setOptions(
				load(getPropertyStream("shortcut.prop")));
		DrawingOption.getInstance().setOptions(
				load(getPropertyStream("draw.prop")));
		DrawingOption.getInstance().initialize();
		CaptureOption.getInstance().setOptions(
				load(getPropertyStream("capture.prop")));
		ZoomOption.getInstance().setOptions(
				load(getPropertyStream("zoom.prop")));
	}
	
	public static void loadDefaultProperty() {
		Version.getInstance().setValues(
				load(getResourceStream("resource/version.prop")));
		ShortcutOption.getInstance().setOptions(
				load(getResourceStream("resource/shortcut.prop")));
		DrawingOption.getInstance().setOptions(
				load(getResourceStream("resource/draw.prop")));
		DrawingOption.getInstance().initialize();
		CaptureOption.getInstance().setOptions(
				load(getResourceStream("resource/capture.prop")));
		ZoomOption.getInstance().setOptions(
				load(getResourceStream("resource/zoom.prop")));
	}
	
	public static void save() {
		File dir = new File(System.getProperty("user.home"), ".hPen");
		File shortcut = new File(dir, "shortcut.prop");
		File draw = new File(dir, "draw.prop");
		File capture = new File(dir, "capture.prop");
		File zoom = new File(dir, "zoom.prop");
		
		try(FileOutputStream out = new FileOutputStream(shortcut)){
			ShortcutOption.getInstance().getOptions().store(out,"");		
		}catch(IOException e) {}
		
		try(FileOutputStream out = new FileOutputStream(draw)){
			DrawingOption.getInstance().getOptions().store(out,"");		
		}catch(IOException e) {}
		
		try(FileOutputStream out = new FileOutputStream(capture)){
			CaptureOption.getInstance().getOptions().store(out,"");		
		}catch(IOException e) {}
		
		try(FileOutputStream out = new FileOutputStream(zoom)){
			ZoomOption.getInstance().getOptions().store(out,"");		
		}catch(IOException e) {}
		
	}
	
	public static boolean createPropertyFolder(){
		File folder = new File(System.getProperty("user.home")+File.separator+".hPen");
		if(folder.isFile())
			folder.delete();
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
	
	public static InputStream getPropertyStream(String name) throws FileNotFoundException {
		File dir = new File(System.getProperty("user.home"), ".hPen");
		File target = new File(dir, name);
		return new FileInputStream(target);
	}
	
	public static InputStream getResourceStream(String uri) {
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

package com.hacademy.hpen.ui.option;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;

import com.hacademy.hpen.util.loader.annotation.Component;

@Component
public class ConfigurationManager {
	
	private final String root = System.getProperty("user.home") + "/.hpen";
	
	public <T>T load(Class<T> clazz) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		File target = new File(root, clazz.getSimpleName());
		try(ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(target)))){
			Object obj = in.readObject();
			return clazz.cast(obj);
		}
		catch(Exception e) {
			return clazz.getDeclaredConstructor().newInstance();
		}
	}
	
	public void save(Object object) throws IOException{
		File dir = new File(root);
		dir.mkdirs();
		File target = new File(dir, object.getClass().getSimpleName());
		try(ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(target)))) {
			out.writeObject(object);
		}
	}
	
}

package com.hacademy.hpen.ui.option;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public abstract class AppConfiguration implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public static final String root = System.getProperty("user.home") + "/.hpen";
	@Setter @Getter
	private String filename;
	
	protected AppConfiguration(String filename) {
		setFilename(filename);
	}
	
	public void load() throws IOException, ClassNotFoundException {
		File file = new File(root, filename);
		if(!file.exists()) throw new FileNotFoundException("["+filename+"] 을 찾을 수 없습니다");
		try(ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))){
			Object obj = in.readObject();
			afterLoad(obj);
		}
	}
	
	public abstract void afterLoad(Object object);
	
	public void save(Object object) throws FileNotFoundException, IOException {
		File file = new File(root, filename);
		try(ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))){
			out.writeObject(object);
		}
	}
}

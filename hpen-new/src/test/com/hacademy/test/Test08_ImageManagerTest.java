package com.hacademy.test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.junit.Before;
import org.junit.Test;

import com.hacademy.hpen.util.image.ImageManager;
import com.hacademy.hpen.util.loader.InMemoryObjectLoader;
import com.hacademy.hpen.util.screen.ScreenManager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test08_ImageManagerTest {

	InMemoryObjectLoader loader;
	ImageManager imManager;
	ScreenManager scManager;
	
	@Before
	public void before() throws IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException {
		loader = new InMemoryObjectLoader("com.hacademy.hpen");
		imManager = loader.getBean(ImageManager.class);
		scManager = loader.getBean(ScreenManager.class);
	}
	
	@Test
	public void test() throws IOException {
		
		imManager.saveImageAsWithDialog(scManager.getCurrentMonitorImage(), null);
	}
	
}

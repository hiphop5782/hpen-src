package com.hacademy.test;

import static org.junit.Assert.assertNotNull;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.imageio.ImageIO;

import org.junit.Before;

import com.hacademy.hpen.util.loader.InMemoryObjectLoader;
import com.hacademy.hpen.util.screen.ScreenManager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test01_ScreenManagerTest {

	InMemoryObjectLoader loader;
	ScreenManager manager;
	
	@Before
	public void before() throws IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException {
		loader = new InMemoryObjectLoader("com.hacademy.hpen");
		manager = loader.getBean(ScreenManager.class);
	}
	
//	@Test
	public void checkMonitorCount() {
		int monitorCount = manager.getMonitorCount();
		log.debug("monitorCount = {}", monitorCount);
	}
	
//	@Test
	public void captureMainMonitor() throws IOException {
		BufferedImage image = manager.getMonitorImage(ScreenManager.MAIN_MONITOR);
		ImageIO.write(image, "png", new File("test-main.png"));
		assertNotNull(image);
	}
	
//	@Test
	public void captureSecondMonitor() throws IOException {
		BufferedImage image = manager.getMonitorImage(ScreenManager.SECOND_MONITOR);
		ImageIO.write(image, "png", new File("test-second.png"));
		assertNotNull(image);
	}
	
//	@Test
	public void captureThirdMonitor() throws IOException {
		BufferedImage image = manager.getMonitorImage(ScreenManager.THIRD_MONITOR);
		ImageIO.write(image, "png", new File("test-third.png"));
		assertNotNull(image);
	}
	
//	@Test
	public void test() {
		Rectangle a = manager.getMonitorRect(ScreenManager.MAIN_MONITOR);
		Rectangle b = manager.getMonitorRect(ScreenManager.SECOND_MONITOR);
		Rectangle c = manager.getMonitorRect(ScreenManager.THIRD_MONITOR);
		
		log.debug("a = {}", a);
		log.debug("b = {}", b);
		log.debug("c = {}", c);
	}
	
}

package com.hacademy.test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;

import org.junit.Before;
import org.junit.Test;

import com.hacademy.hpen.ui.capture.CaptureFullScreenFrame;
import com.hacademy.hpen.util.loader.InMemoryObjectLoader;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test05_AbstractFrameTest {
	InMemoryObjectLoader loader;
	CaptureFullScreenFrame frame;
	
	@Before
	public void before() throws IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException {
		loader = new InMemoryObjectLoader("com.hacademy.hpen");
		log.debug("components = {}", loader.componentSize());
		frame = loader.getBean(CaptureFullScreenFrame.class);
	}
	
	@Test
	public void test() throws InterruptedException {
		frame.open();
		Thread.currentThread().join();
	}
	
}

package com.hacademy.test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.junit.Before;
import org.junit.Test;

import com.hacademy.hpen.ui.option.CaptureConfiguration;
import com.hacademy.hpen.ui.option.ConfigurationManager;
import com.hacademy.hpen.util.loader.InMemoryObjectLoader;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test06_ConfigurationManagerTest {
	InMemoryObjectLoader loader;
	ConfigurationManager manager;
	
	@Before
	public void before() throws IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException{
		loader = new InMemoryObjectLoader("com.hacademy.hpen");
		manager = loader.getBean(ConfigurationManager.class);
	}

	@Test
	public void test() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException {
		CaptureConfiguration conf = manager.load(CaptureConfiguration.class);
		log.info("conf = {}", conf);
		conf.setBorderThickness(3);
		manager.save(conf);
		conf = manager.load(CaptureConfiguration.class);
		log.info("conf = {}", conf);
	}
}

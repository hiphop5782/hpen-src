package com.hacademy.test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.junit.Before;
import org.junit.Test;

import com.hacademy.hpen.util.keyboard.GlobalKeyboardManager;
import com.hacademy.hpen.util.loader.InMemoryObjectLoader;

import lc.kra.system.keyboard.event.GlobalKeyEvent;

public class Test12_GlobalKeyboardManagerTest {
	InMemoryObjectLoader loader;
	GlobalKeyboardManager keyManager;
	
	@Before
	public void before() throws IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException {
		loader = new InMemoryObjectLoader("com.hacademy.hpen");
		keyManager = loader.getBean(GlobalKeyboardManager.class);
	}
	
	@Test
	public void test() throws InterruptedException {
		keyManager.addKey(GlobalKeyEvent.VK_LWIN, ()->{
			System.out.println("press window");
		});
		Thread.currentThread().join();
	}
}

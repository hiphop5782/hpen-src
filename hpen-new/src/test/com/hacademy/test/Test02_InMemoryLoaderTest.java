package com.hacademy.hpen;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.junit.Before;
import org.junit.Test;

import com.hacademy.hpen.util.loader.InMemoryObjectLoader;
import com.hacademy.hpen.util.loader.testobj.TestObjectA;
import com.hacademy.hpen.util.loader.testobj.TestObjectB;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test02_InMemoryLoaderTest {
	
	InMemoryObjectLoader loader;
	
	@Before
	public void before() throws IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException {
		loader = new InMemoryObjectLoader("com.hacademy.hpen");
	}
	
	@Test
	public void test() {
		log.debug("loader = {}", loader);
		log.debug("componentSize = {}", loader.componentSize());
		assertNotEquals(loader.componentSize(), 0);
		
		TestObjectA a = loader.getBean(TestObjectA.class);
		TestObjectB b = loader.getBean(TestObjectB.class);
		assertNotNull(a);
		assertNotNull(b);
	}
	
}

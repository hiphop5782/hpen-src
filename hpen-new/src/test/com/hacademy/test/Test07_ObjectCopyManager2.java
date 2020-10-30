package com.hacademy.test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.junit.Before;
import org.junit.Test;

import com.hacademy.hpen.util.loader.InMemoryObjectLoader;
import com.hacademy.test.util.loader.testobj.ObjectCopyManager;
import com.hacademy.test.util.loader.testobj.TestObjectA;
import com.hacademy.test.util.loader.testobj.TestObjectB;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test07_ObjectCopyManager2 {
	InMemoryObjectLoader loader;
	ObjectCopyManager manager;
	TestObjectA a;
	TestObjectB b;
	@Before
	public void before() throws IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException {
		loader = new InMemoryObjectLoader("com.hacademy.test.util.loader.testobj");
		manager = loader.getBean(ObjectCopyManager.class);
		a = loader.getBean(TestObjectA.class);
		b = loader.getBean(TestObjectB.class);
	}
	@Test
	public void test() throws IllegalArgumentException, IllegalAccessException {
		TestObjectA newA = new TestObjectA();
		log.debug("manager = {}", manager);
		
		manager.copy(a, newA);
		log.debug("a = {}", a);
		log.debug("newA = {}", newA);
		log.debug("newA.b = {}", newA.getB());
	}
}

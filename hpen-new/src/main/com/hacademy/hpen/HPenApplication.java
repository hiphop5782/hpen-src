package com.hacademy.hpen;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import com.hacademy.hpen.util.loader.InMemoryObjectLoader;

public class HPenApplication {
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException {
		InMemoryObjectLoader loader = new InMemoryObjectLoader("com.hacademy.hpen");
	}
}

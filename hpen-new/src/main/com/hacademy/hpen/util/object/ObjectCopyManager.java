package com.hacademy.hpen.util.object;

import java.lang.reflect.Field;

import com.hacademy.hpen.util.loader.annotation.Component;

@Component
public class ObjectCopyManager {
	public <T>T copy(T a, T b) throws IllegalArgumentException, IllegalAccessException {
		if(a.getClass() != b.getClass()) throw new IllegalArgumentException("Diffrent Type");
		
		for(Field field : a.getClass().getDeclaredFields()) {
			try {
				field.setAccessible(true);
				field.set(b, field.get(a));
			}
			catch(Exception e) {}
		}
		return b;
	}
}

package com.hacademy.test.util.loader.testobj;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.hacademy.hpen.util.loader.annotation.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ObjectCopyManager {
	public <T>T copy(T a, T b) throws IllegalArgumentException, IllegalAccessException {
		if(a.getClass() != b.getClass()) throw new IllegalArgumentException("Diffrent Type");
		
		for(Field field : a.getClass().getDeclaredFields()) {
			log.debug("field = {}", field);
			try {
				field.setAccessible(true);
				
				//final 항목은 pass
				if(Modifier.isFinal(field.getModifiers())) {
					continue;
				}
				
				Field copyField = b.getClass().getDeclaredField(field.getName());
				log.debug("copyField = {}", copyField);
				copyField.setAccessible(true);
				copyField.set(b, field.get(a));
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		return b;
	}
}

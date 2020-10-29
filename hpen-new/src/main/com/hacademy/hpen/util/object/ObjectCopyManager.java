package com.hacademy.hpen.util.object;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.hacademy.hpen.util.loader.annotation.Component;

@Component
public class ObjectCopyManager {
	public <T>T copy(T a, T b) throws IllegalArgumentException, IllegalAccessException {
		if(a.getClass() != b.getClass()) throw new IllegalArgumentException("Diffrent Type");
		
		for(Field field : a.getClass().getDeclaredFields()) {
			try {
				field.setAccessible(true);
				
				//final 항목은 pass
				if(Modifier.isFinal(field.getModifiers())) {
					continue;
				}
				
				Field copyField = b.getClass().getField(field.getName());
				copyField.setAccessible(true);
				copyField.set(null, field.get(a));
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		return b;
	}
}

package com.hacademy.test;

import org.junit.Test;

import com.hacademy.hpen.util.object.ObjectCopyManager;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test07_ObjectCopyManager {
	@Data @AllArgsConstructor @Builder
	static class Student{
		String name;
		int score;
	}
	
	@Test
	public void test() throws IllegalArgumentException, IllegalAccessException {
		ObjectCopyManager manager = new ObjectCopyManager();
		Student a = Student.builder().name("테스트").score(50).build();
		Student b = Student.builder().build();
		
		manager.copy(a, b);
		
		log.info("a = {}", a);
		log.info("b = {}", b);
	}
}

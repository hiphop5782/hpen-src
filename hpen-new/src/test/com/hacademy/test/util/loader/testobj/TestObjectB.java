package com.hacademy.test.util.loader.testobj;

import com.hacademy.hpen.util.loader.annotation.Autowired;
import com.hacademy.hpen.util.loader.annotation.Component;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude= {"a"})
@Component
public class TestObjectB {
	int b = 20;
	@Autowired
	private TestObjectA a;
}

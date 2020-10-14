package com.hacademy.hpen.util.loader.testobj;

import com.hacademy.hpen.util.loader.annotation.Autowired;
import com.hacademy.hpen.util.loader.annotation.Component;

@Component
public class TestObjectA {
	int a = 10;
	@Autowired
	private TestObjectB b;
}

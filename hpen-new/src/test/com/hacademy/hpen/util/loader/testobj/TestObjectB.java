package com.hacademy.hpen.util.loader.testobj;

import com.hacademy.hpen.util.loader.annotation.Autowired;
import com.hacademy.hpen.util.loader.annotation.Component;

@Component
public class TestObjectB {
	int b = 20;
	@Autowired
	private TestObjectA a;
}

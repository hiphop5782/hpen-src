package com.hacademy.test.util.loader.testobj;

import com.hacademy.hpen.util.loader.annotation.Autowired;
import com.hacademy.hpen.util.loader.annotation.Component;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Data
@ToString(exclude= {"b"})
@Component
public class TestObjectA {
	int a = 10;
	@Autowired
	private TestObjectB b;
}

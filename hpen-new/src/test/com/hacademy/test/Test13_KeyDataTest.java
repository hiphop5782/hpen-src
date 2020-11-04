package com.hacademy.test;

import org.junit.Test;

import com.hacademy.hpen.util.keyboard.KeyData;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test13_KeyDataTest {

	@Test
	public void test() {
		KeyData data = KeyData.valueOf("shift ctrl win alt t");
		log.debug("data = {}", data);
	}
	
}

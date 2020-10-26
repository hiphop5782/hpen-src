package com.hacademy.test;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test06_EntityManagerTest {
	
	SqlSessionFactory factory;
	SqlSession sqlSession;
	
	@Before
	public void before() throws IOException {
		InputStream in = Resources.getResourceAsStream("com/hacademy/hpen/database/mybatis-config.xml");
		factory = new SqlSessionFactoryBuilder().build(in); 
		sqlSession = factory.openSession(true);
	}
	
	@Test
	public void test() {
		log.info("test clear");
	}
}

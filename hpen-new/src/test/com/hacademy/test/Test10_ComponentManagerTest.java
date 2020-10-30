package com.hacademy.test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.junit.Before;
import org.junit.Test;

import com.hacademy.hpen.ui.option.CaptureConfigurationPanel;
import com.hacademy.hpen.util.loader.InMemoryObjectLoader;
import com.jtattoo.plaf.mcwin.McWinLookAndFeel;

public class Test10_ComponentManagerTest {
	
	InMemoryObjectLoader loader;
	JPanel panel;
	
	@Before
	public void before() throws IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException {
		loader = new InMemoryObjectLoader("com.hacademy.hpen");
		panel = loader.getBean(CaptureConfigurationPanel.class);
	}
	
	@Test
	public void test() throws InterruptedException, UnsupportedLookAndFeelException{
		UIManager.setLookAndFeel(new McWinLookAndFeel());
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(550, 550);
		frame.setContentPane(panel);
		frame.setVisible(true);
		System.out.println(this.getClass().getName());
		Thread.currentThread().join();
	}
	
}

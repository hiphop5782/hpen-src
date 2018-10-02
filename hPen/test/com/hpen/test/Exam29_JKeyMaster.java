package com.hpen.test;

import javax.swing.KeyStroke;

import com.sun.glass.events.KeyEvent;
import com.tulskiy.keymaster.common.Provider;

public class Exam29_JKeyMaster {
	public static void main(String[] args) {
		Provider provider = Provider.getCurrentProvider(true);
		provider.register(KeyStroke.getKeyStroke(KeyEvent.VK_1, 0), e->{
			System.out.println(e);
		});
	}
}

package com.hpen.test;

import java.awt.Desktop;
import java.io.File;

public class Exam11_튜토리얼실행 {
	public static void main(String[] args) throws Exception{
		Desktop desktop = Desktop.getDesktop();
		desktop.edit(new File("tutorial.txt"));
	}
}

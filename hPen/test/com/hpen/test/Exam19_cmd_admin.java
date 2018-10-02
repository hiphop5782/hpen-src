package com.hpen.test;

import java.io.File;

import com.hpen.jna.Elevator;

public class Exam19_cmd_admin {
	public static void main(String[] args) {
		String name = "tutorial.txt";
		File f = new File(name);
		System.out.println(f.exists());
		String command = "copy /Y "+name+" \"%PROGRAMDATA%\\MicroSoft\\Windows\\Start Menu\\Programs\\Startup\\"+name+"\"";
		System.out.println(command);
		Elevator.runCommandAsAdmin(command);
	}
}

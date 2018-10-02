package com.hpen.test;

import java.awt.GraphicsDevice;
import java.awt.MouseInfo;

public class Exam08_모니터위치확인 {
	public static void main(String[] args) {
		while(true){
			GraphicsDevice device = MouseInfo.getPointerInfo().getDevice();
			String idString = device.getIDstring();
			System.out.println(idString.substring(idString.length()-1));
			System.out.println(idString.charAt(idString.length()-1));
			try{
				Thread.sleep(100);
			}catch(Exception e){}
		}
	}
}

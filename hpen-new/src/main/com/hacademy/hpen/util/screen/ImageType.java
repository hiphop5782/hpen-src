package com.hacademy.hpen.util.screen;

public enum ImageType {
	PNG("png"), JPG("jpg");
	
	String value;
	ImageType(String value){
		this.value = value;
	}
	public String getValue() {
		return value;
	}
}
